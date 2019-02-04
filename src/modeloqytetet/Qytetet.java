/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 *
 * @author Ray
 */
public class Qytetet
{
    private static final Qytetet instance = new Qytetet();
    
    private ArrayList<Sorpresa> mazo;
    private Tablero tablero;
    public static final int MAX_JUGADORES = 4;
    public static final int MIN_JUGADORES = 2;
    static final int NUM_SORPRESAS = 10;
    public static final int NUM_CASILLAS = 20;
    static final int PRECIO_LIBERTAD = 200;
    static final int SALDO_SALIDA = 1000;
    private Sorpresa cartaActual;
    private Jugador jugadorActual;
    private Dado dado;
    private ArrayList<Jugador> jugadores;
    private EstadoJuego estado;
    
    public Random random = new Random();
    
    private Qytetet()
    {
        mazo = new ArrayList<>();
        this.inicializarTablero();
        this.inicializarCartasSorpresa();
        jugadores = new ArrayList<>();
        this.cartaActual = null;
        this.jugadorActual = null;
        this.dado = Dado.getInstance();
        this.estado = null;
    }
    
    public static Qytetet getInstance()
    {
        return instance;
    }
    
    ArrayList<Sorpresa> getMazo()
    {
        return mazo;
    }
    
    public Tablero getTablero()
    {
        return tablero;
    }
    
    private void inicializarTablero()
    {
        tablero = new Tablero();
    }
    
    private void inicializarCartasSorpresa()
    {
        if(mazo.isEmpty() == false)
            mazo.clear();
        
        mazo.add(new Sorpresa("La DGT te multa con 150 euros por exceso de velocidad", -150, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("La ciudad de Qytetilandia de recompensa con 200 euros por ayudar a mantener sus calles limpias", 200, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Teleport a casilla 2", 2, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Te has tropezado con una piedra y avanzas 2 casillas", 2, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("A la carcel por trafico de estupefacientes", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("El ayuntamiento de la ciudad te recompensa con 30 euros por cada casa/hotel en tu propiedad. Gracias por hacer Qytetilandia más bonita con tus edificaciones!", 30, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Llegan las facturas. Paga 15 euros por cada casa/hotel de tu propiedad", -15, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Te han pillado haciendo trampas, gánate el perdón sobornando con 10 euros a cada jugador", 10, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Hoy es el mejor día de tu vida, cada jugador te dará 12 euros", -12, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Este salvoconducto te permite salir de la cárcel", 0, TipoSorpresa.SALIRCARCEL));
        mazo.add(new Sorpresa("especulador", 3000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa("especulador", 5000, TipoSorpresa.CONVERTIRME));
        
        barajarCartas();
    }
    
    private void barajarCartas()
    {
        ArrayList<Sorpresa> aux = new ArrayList<>();
        aux.addAll(mazo);
        mazo.removeAll(aux);
        
        while(aux.size() > 0)
        {
            mazo.add(aux.remove(random.nextInt(aux.size())));
        }
    }
    
    void actuarSiEnCasillaEdificable()
    {
        boolean deboPagar = jugadorActual.deboPagarAlquiler();
        
        if(deboPagar)
        {
            jugadorActual.pagarAlquiler();
            
            if(jugadorActual.getSaldo() <= 0)
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCAROTA);
        }
        
        Casilla casilla = obtenerCasillaJugadorActual();
        
        boolean tengoPropietario = ( (Calle)casilla ).tengoPropietario();
        
        if(estado != EstadoJuego.ALGUNJUGADORENBANCAROTA)
        {
            if(tengoPropietario)
                setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
            else
                setEstadoJuego(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
        }
    }
    
    void actuarSiEnCasillaNoEdificable()
    {
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        Casilla casillaActual = jugadorActual.getCasillaActual();
        
        if(casillaActual.getTipo() == TipoCasilla.IMPUESTO)
            jugadorActual.pagarImpuesto();
        else if(casillaActual.getTipo() == TipoCasilla.JUEZ)
            encarcelarJugador();
        else if(casillaActual.getTipo() == TipoCasilla.SORPRESA)
        {
            cartaActual = mazo.remove(0);
            setEstadoJuego(EstadoJuego.JA_CONSORPRESA);
        }
    }
    
    public void aplicarSorpresa()
    {
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        if(cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL)
            jugadorActual.setCartaLibertad(cartaActual);
        else
        {
            mazo.add(cartaActual);
            
            if(cartaActual.getTipo() == TipoSorpresa.PAGARCOBRAR)
            {
                jugadorActual.modificarSaldo(cartaActual.getValor());
                if(jugadorActual.getSaldo() <= 0)
                    setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCAROTA);
            }
            else if(cartaActual.getTipo() == TipoSorpresa.IRACASILLA)
            {
                int valor = cartaActual.getValor();
                boolean casillaCarcel = tablero.esCasillaCarcel(valor);

                if(casillaCarcel)
                    encarcelarJugador();
                else
                    mover(valor);
            }
            else if(cartaActual.getTipo() == TipoSorpresa.PORCASAHOTEL)
            {
                int cantidad = cartaActual.getValor();
                int numeroTotal = jugadorActual.cuantasCasasHotelesTengo();
                jugadorActual.modificarSaldo(cantidad * numeroTotal);

                if(jugadorActual.getSaldo() <= 0)
                    setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCAROTA);
            }
            else if(cartaActual.getTipo() == TipoSorpresa.PORJUGADOR)
            {
                for(int i = 0; i < jugadores.size(); i++)
                {
                    Jugador jugador = jugadores.get(i);
                    if(jugador != jugadorActual)
                        jugador.modificarSaldo(cartaActual.getValor());
                    if(jugador.getSaldo() <= 0)
                        setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCAROTA);

                    jugadorActual.modificarSaldo(-cartaActual.getValor());
                    if(jugadorActual.getSaldo() <= 0)
                        setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCAROTA);

                    jugadores.remove(i);
                    jugadores.add(i, jugador);
                }
            }
            else if(cartaActual.getTipo() == TipoSorpresa.CONVERTIRME)
            {
                int pos = jugadores.indexOf(jugadorActual);
                
                jugadores.set(pos, jugadorActual.convertirme(cartaActual.getValor()));
                
                jugadorActual = jugadores.get(pos);
            }
        }
    }
    
    public boolean cancelarHipoteca(int numeroCasilla)
    {
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        
        return jugadorActual.cancelarHipoteca(casilla.getTitulo());
    }
    
    public boolean comprarTituloPropiedad()
    {
        boolean comprado = jugadorActual.comprarTituloPropiedad();
        
        if(comprado)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return comprado;
    }
    
    public boolean edificarCasa(int numeroCasilla)
    {        
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        boolean edificada = jugadorActual.edificarCasa(titulo);
        
        if(edificada)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return edificada;
    }
    
    public boolean edificarHotel(int numeroCasilla)
    {
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        boolean edificada = jugadorActual.edificarHotel(titulo);
        
        if(edificada)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return edificada;
    }
    
    private void encarcelarJugador()
    {
        if(jugadorActual.deboIrACarcel())
        {
            Casilla casillaCarcel = tablero.getCarcel();
            jugadorActual.irACarcel(casillaCarcel);
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }
        else
        {
            Sorpresa carta = jugadorActual.devolverCartaLibertad();
            mazo.add(carta);
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
    }
    
    public Sorpresa getCartaActual()
    {
        return cartaActual;
    }
    
    Dado getDado()
    {
        return dado;
    }
    
    public EstadoJuego getEstadoJuego()
    {
        return estado;
    }
    
    public Jugador getJugadorActual()
    {
        return jugadorActual;
    }
    
    public ArrayList<Jugador> getJugadores()
    {
        return jugadores;
    }
    
    public int getValorDado()
    {
        return Dado.getInstance().getValor();
    }
    
    public void hipotecarPropiedad(int numeroCasilla)
    {
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        jugadorActual.hipotecarPropiedad(titulo);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
    }
    
    public void inicializarJuego(ArrayList<String> nombres)
    {
        assert nombres.size() > MAX_JUGADORES || nombres.size() < MIN_JUGADORES;
        
        if(nombres.size() <= MAX_JUGADORES && nombres.size() >= MIN_JUGADORES)
        {
            this.inicializarJugadores(nombres);
            this.inicializarTablero();
            this.inicializarCartasSorpresa();
            this.salidaJugadores();
        }
        else
            System.err.println("Número de jugadores incorrecto. Qytetet (2-4 jugadores)");
    }
    
    private void inicializarJugadores(ArrayList<String> nombres)
    {
        if(jugadores.size() == 0)
            for(int i = 0; i < nombres.size(); i++)
                jugadores.add(new Jugador(nombres.get(i)));
    }
    
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo)
    {
        if(metodo == MetodoSalirCarcel.TIRANDODADO)
        {
            int resultado = dado.tirar();
            
            if(resultado >= 5)
                jugadorActual.setEncarcelado(false);
        }
        else if(metodo == MetodoSalirCarcel.PAGANDOLIBERTAD)
        {
            jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
        }
        
        boolean encarcelado = jugadorActual.getEncarcelado();
        
        if(encarcelado)
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        else
            setEstadoJuego(EstadoJuego.JA_PREPARADO);
        
        return !encarcelado;
    }
    
    public void jugar()
    {
        int casillaDestino = tablero.obtenerCasillaFinal(jugadorActual.getCasillaActual(), this.tirarDado()).getNumeroCasilla();
        mover(casillaDestino);
    }
    
    void mover(int numCasillaDestino)
    {
        Casilla casillaInicial = jugadorActual.getCasillaActual();
        Casilla casillaFinal = tablero.obtenerCasillaNumero(numCasillaDestino);
        jugadorActual.setCasillaActual(casillaFinal);
        
        if(numCasillaDestino < casillaInicial.getNumeroCasilla())
            jugadorActual.modificarSaldo(SALDO_SALIDA);
        
        if(casillaFinal.soyEdificable())
            actuarSiEnCasillaEdificable();
        else
            actuarSiEnCasillaNoEdificable();
    }
    
    public Casilla obtenerCasillaJugadorActual()
    {
        return jugadorActual.getCasillaActual();
    }
    
    public ArrayList<Casilla> obtenerCasillasTablero()
    {
        return tablero.getCasillas();
    }
    
    public ArrayList<Integer> obtenerPropiedadesJugador()
    {
        ArrayList<Integer> aux = new ArrayList<>();
        
        for(Casilla c: tablero.getCasillas())
            if(c.getTitulo() != null)
                if(c.getTitulo().getPropietario() == jugadorActual)
                    aux.add(c.getNumeroCasilla());
        
        return aux;
    }
    
    public ArrayList<Integer> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean estadoHipoteca)
    {
        ArrayList<Integer> aux = new ArrayList<>();
        
        if(estadoHipoteca)
        {
            for(Casilla c: tablero.getCasillas())
                if(c.getTitulo() != null)
                    if(c.getTitulo().getPropietario() == jugadorActual && c.getTitulo().getHipotecada())
                        aux.add(c.getNumeroCasilla());
        }
        else
        {
            for(Casilla c: tablero.getCasillas())
                if(c.getTitulo() != null)
                    if(c.getTitulo().getPropietario() == jugadorActual && !c.getTitulo().getHipotecada())
                        aux.add(c.getNumeroCasilla());
        }
        
        return aux;
    }
    
    public void obtenerRanking()
    {
        Collections.sort(jugadores);
    }
    
    public int obtenerSaldoJugadorActual()
    {
        return jugadorActual.getSaldo();
    }
    
    private void salidaJugadores()
    {
        for(Jugador j: jugadores)
            j.setCasillaActual(tablero.obtenerCasillaNumero(0));
        
        jugadorActual = jugadores.get(random.nextInt(jugadores.size()));
        
        estado = EstadoJuego.JA_PREPARADO;
    }
    
    private void setCartaActual(Sorpresa cartaActual)
    {
        this.cartaActual = cartaActual;
    }
    
    public void setEstadoJuego(EstadoJuego estadoJuego)
    {
        this.estado = estadoJuego;
    }
    
    public  void siguienteJugador()
    {
        jugadorActual = jugadores.get((jugadores.indexOf(jugadorActual)+1) % jugadores.size());
        
        if(jugadorActual.getEncarcelado())
            estado = EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD;
        else
            estado = EstadoJuego.JA_PREPARADO;
    }
    
    int tirarDado()
    {
        return Dado.getInstance().tirar();
    }
    
    public void venderPropiedad(int numeroCasilla)
    {
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        jugadorActual.venderPropiedad(casilla);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
    }

    @Override
    public String toString()
    {
        String salida = "\nQYTETET\n\n" + tablero + "\nSorpresas:";
        
        for (Sorpresa s: mazo)
            salida += "\n" + s.toString();
        
        salida += "\n\ncarta actual=" + cartaActual + "\ndado=" + dado + "\njugador actual=\n\n" + jugadorActual + "\n\njugadores:\n";
        for(Jugador j: jugadores)
            salida += '\n' + j.toString();
        salida += '\n';
        return salida;//"Qytetet{" + "mazo=" + mazo + ", tablero=" + tablero + ", cartaActual=" + cartaActual + ", jugadorActual=" + jugadorActual + ", dado=" + dado + ", jugadores=" + jugadores + '}';
    }
}