/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;

/**
 *
 * @author ray
 */
public class Jugador implements Comparable
{
    private String nombre;
    private int saldo;
    private Casilla casillaActual;
    private boolean encarcelado;
    private Sorpresa cartaLibertad;
    private ArrayList<TituloPropiedad> propiedades;
    
    Jugador(String nombre)
    {
        encarcelado = false;
        this.nombre = nombre;
        saldo = 7500;
        propiedades = new ArrayList<>();
        this.casillaActual = null;
        this.cartaLibertad = null;
    }
    
    protected Jugador(Jugador jugador)
    {
        this.nombre = jugador.nombre;
        this.saldo = jugador.saldo;
        this.casillaActual = jugador.casillaActual;
        this.encarcelado = jugador.encarcelado;
        this.cartaLibertad = jugador.cartaLibertad;
        this.propiedades = jugador.propiedades;
    }

    Sorpresa getCartaLibertad()
    {
        return cartaLibertad;
    }

    boolean getEncarcelado()
    {
        return encarcelado;
    }

    Casilla getCasillaActual()
    {
        return casillaActual;
    }

    public String getNombre()
    {
        return nombre;
    }

    public int getSaldo()
    {
        return saldo;
    }

    ArrayList<TituloPropiedad> getPropiedades()
    {
        return propiedades;
    }

    void setEncarcelado(boolean encarcelado)
    {
        this.encarcelado = encarcelado;
    }

    void setCartaLibertad(Sorpresa cartaLibertad)
    {
        this.cartaLibertad = cartaLibertad;
    }

    void setCasillaActual(Casilla casillaActual)
    {
        this.casillaActual = casillaActual;
    }
    
    boolean cancelarHipoteca(TituloPropiedad titulo)
    {
        boolean conseguido;
        int costeCancelar = titulo.calcularCosteCancelar();
        if(tengoSaldo(costeCancelar))
        {
            titulo.cancelarHipoteca();
            modificarSaldo(-costeCancelar);
            conseguido = true;
        }
        else
            conseguido = false;
        
        return conseguido;
    }
    
    protected Especulador convertirme(int fianza)
    {
        Especulador especulador = new Especulador(this, fianza);
        
        return especulador;
    }
    
    boolean comprarTituloPropiedad()
    {
        boolean comprado = false;
        
        int costeCompra = casillaActual.getCoste();
        
        if(costeCompra < saldo)
        {
            comprado = true;
            
            TituloPropiedad titulo = ( (Calle)casillaActual ).asignarPropietario(this);
            
            propiedades.add(titulo);
            
            modificarSaldo(-costeCompra);
        }
        
        return comprado;
    }
    
    int cuantasCasasHotelesTengo()
    {
        int total = 0;
        
        for(TituloPropiedad t: propiedades)
            total += t.getNumCasas() + t.getNumHoteles();
        
        return total;
    }
    
    protected boolean deboIrACarcel()
    {
        return !this.tengoCartaLibertad();
    }
    
    boolean deboPagarAlquiler()
    {
        boolean tienePropietario, enCarcel, estaHipotecada = true;
        TituloPropiedad titulo = casillaActual.getTitulo();
        boolean esDeMiPropiedad = esDeMiPropiedad(titulo);
        
        if(!esDeMiPropiedad)
        {
            tienePropietario = titulo.tengoPropietario();
            if(tienePropietario)
            {
                enCarcel = titulo.propietarioEncarcelado();
                if(!enCarcel)
                    estaHipotecada = titulo.getHipotecada();
            }
        }
        
        return !estaHipotecada;
    }
    
    Sorpresa devolverCartaLibertad()
    {
        Sorpresa aux = null;
        
        if(this.tengoCartaLibertad())
            aux = cartaLibertad;
        
        cartaLibertad = null;
        
        return aux;
    }
    
    boolean edificarCasa(TituloPropiedad titulo)
    {
        boolean edificada = false;
        
        if(this.puedoEdificarCasa(titulo))
        {
            int costeEdificarCasa = titulo.getPrecioEdificar();
            
            titulo.edificarCasa();
            modificarSaldo(-costeEdificarCasa);
            edificada = true;
        }
        
        return edificada;
    }
    
    boolean edificarHotel(TituloPropiedad titulo)
    {
        boolean edificada = false;
        
        if(this.puedoEdificarHotel(titulo))
        {
            int costeEdificarHotel = titulo.getPrecioEdificar();
            
            titulo.edificarHotel();
            modificarSaldo(-costeEdificarHotel);
            edificada = true;
        }
        
        return edificada;
    }
    
    private void eliminarDeMisPropiedades(TituloPropiedad titulo)
    {
        propiedades.remove(titulo);
        titulo.setPropietario(null);
    }
    
    private boolean esDeMiPropiedad(TituloPropiedad titulo)
    {
        boolean encontrado = false;
        int i = 0;
        
        while(!encontrado && i < propiedades.size())
        {
            if(this.propiedades.get(i) == titulo)
                encontrado = true;
            else
                i++;
        }
        
        return encontrado;
    }
    
    boolean estoyEnCalleLibre()
    {
        throw new UnsupportedOperationException("Sin implementar");
    }
    
    void hipotecarPropiedad(TituloPropiedad titulo)
    {
        int costeHipoteca = titulo.hipotecar();
        
        modificarSaldo(costeHipoteca);
    }
    
    void irACarcel(Casilla casilla)
    {
        setCasillaActual(casilla);
        setEncarcelado(true);
    }
    
    int modificarSaldo(int cantidad)
    {
        return saldo += cantidad;
    }
    
    int obtenerCapital()
    {
        int capital = saldo;
        
        for(TituloPropiedad t: propiedades)
        {
            capital += t.getPrecioCompra() + (t.getNumCasas() + t.getNumHoteles()) * t.getPrecioEdificar();
            
            if(t.getHipotecada())
                capital -= t.getHipotecaBase();
        }
        
        return capital;
    }
    
    ArrayList<TituloPropiedad> obtenerPropiedades(boolean hipotecada)
    {
        ArrayList<TituloPropiedad> aux = new ArrayList<>();
        
        if(hipotecada)
            for(TituloPropiedad t: propiedades)
            {
                if(t.getHipotecada())
                    aux.add(t);
            }
        else
            for(TituloPropiedad t: propiedades)
            {
                if(!t.getHipotecada())
                    aux.add(t);
            }
        
        return aux;
    }
    
    void pagarAlquiler()
    {
        int costeAlquiler = ( (Calle)casillaActual ).pagarAlquiler();
        
        modificarSaldo(-costeAlquiler);
    }
    
    void pagarImpuesto()
    {
        this.saldo -= casillaActual.getCoste();
    }
    
    void pagarLibertad(int cantidad)
    {
        boolean tengoSaldo = tengoSaldo(cantidad);
        
        if(tengoSaldo)
        {
            setEncarcelado(false);
            modificarSaldo(-cantidad);
        }
    }
    
    protected boolean puedoEdificarCasa(TituloPropiedad titulo)
    {
        if(titulo.getNumCasas() < 4 && titulo.getPropietario() == this && this.tengoSaldo(titulo.getPrecioEdificar()))
            return true;
        else
            return false;
    }
    
    protected boolean puedoEdificarHotel(TituloPropiedad titulo)
    {
        if(titulo.getNumCasas() == 4 && titulo.getNumHoteles() < 4 && titulo.getPropietario() == this && this.tengoSaldo(titulo.getPrecioEdificar()))
            return true;
        else
            return false;
    }
    
    boolean tengoCartaLibertad()
    {
        return this.cartaLibertad != null;
    }
    
    private boolean tengoSaldo(int cantidad)
    {
        return saldo >= cantidad;
    }
    
    void venderPropiedad(Casilla casilla)
    {
        TituloPropiedad titulo = casilla.getTitulo();
        eliminarDeMisPropiedades(titulo);
        int precioVenta = titulo.calcularPrecioVenta();
        modificarSaldo(precioVenta);
    }

    @Override
    public String toString()
    {
        String salida = "Jugador:" + "\nnombre=" + nombre + "\nsaldo=" + saldo + "\ncapital=" + this.obtenerCapital() + "\ncasillaActual=" + casillaActual + "\nencarcelado=" + encarcelado + "\ncartaLibertad=" + cartaLibertad + "\npropiedades:\n";
        for(TituloPropiedad t: propiedades)
            salida = salida + "\n" + t.toString();
        salida = salida + '\n';
        
        return salida;
    }
    
    @Override
    public int compareTo(Object otroJugador)
    {
        int otroCapital = ((Jugador) otroJugador).obtenerCapital();
        return otroCapital - obtenerCapital();
    }
}
