/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorqytetet;

import java.util.ArrayList;
import modeloqytetet.EstadoJuego;
import modeloqytetet.MetodoSalirCarcel;
import modeloqytetet.Qytetet;

/**
 *
 * @author ray
 */
public class ControladorQytetet
{
    private static final ControladorQytetet instance = new ControladorQytetet();
    
    private ArrayList<String> nombreJugadores;
    private Qytetet modelo;
    
    public static ControladorQytetet getInstance()
    {
        return instance;
    }
    
    private ControladorQytetet()
    {
        nombreJugadores = new ArrayList<>();
        modelo = Qytetet.getInstance();
    }
    
    public void setNombreJugadores(ArrayList<String> nombreJugadores)
    {
        this.nombreJugadores = nombreJugadores;
    }
    
    public ArrayList<Integer> obtenerOperacionesJuegoValidas()
    {
        ArrayList<Integer> operaciones = new ArrayList<>();
        
        operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
        operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
        operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
        operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
        int hola;
        if(modelo.getEstadoJuego() == null)
            hola = 7;
        else
            hola = modelo.getEstadoJuego().ordinal();
        switch(hola)
        {
            case 0:
                operaciones.add(OpcionMenu.APLICARSORPRESA.ordinal());
                break;
            case 1:
                operaciones.add(OpcionMenu.OBTENERRANKING.ordinal());
                break;
            case 2:
                operaciones.add(OpcionMenu.PASARTURNO.ordinal());
                operaciones.add(OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal());
                operaciones.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                operaciones.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                operaciones.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                operaciones.add(OpcionMenu.EDIFICARCASA.ordinal());
                operaciones.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                break;
            case 3:
                operaciones.add(OpcionMenu.PASARTURNO.ordinal());
                operaciones.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                operaciones.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                operaciones.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                operaciones.add(OpcionMenu.EDIFICARCASA.ordinal());
                operaciones.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                break;
            case 4:
                operaciones.add(OpcionMenu.JUGAR.ordinal());
                break;
            case 5:
                operaciones.add(OpcionMenu.PASARTURNO.ordinal());
                break;
            case 6:
                operaciones.add(OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal());
                operaciones.add(OpcionMenu.INTENTARSALIRCARCELTIRANDODADO.ordinal());
                break;
            case 7:
                operaciones.add(OpcionMenu.INICIARJUEGO.ordinal());
                break;
        }
        
        return operaciones;
    }
    
    public boolean necesitaElegirCasilla(int opcionMenu)
    {
        boolean necesita = false;
        
        ArrayList<Integer> necesitanCasilla = new ArrayList<>();
        necesitanCasilla.add(6);
        necesitanCasilla.add(7);
        necesitanCasilla.add(8);
        necesitanCasilla.add(9);
        necesitanCasilla.add(10);
        
        for(Integer i : necesitanCasilla)
            if(i == opcionMenu)
                necesita = true;
        
        return necesita;
    }
    
    public ArrayList<Integer> obtenerCasillasValidas(int opcionMenu)
    {
        ArrayList<Integer> casillas = new ArrayList<>();
        
        switch(opcionMenu)
        {
            case 6:
                casillas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
                break;
            case 7:
                casillas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true);
                break;
            case 8:
                casillas = modelo.obtenerPropiedadesJugador();
                break;
            case 9:
                casillas = modelo.obtenerPropiedadesJugador();
                break;
            case 10:
                casillas = modelo.obtenerPropiedadesJugador();
                break;
        }
        
        return casillas;
    }
    
    public String realizarOperacion(int opcionElegida, int casillaElegida)
    {
        String info = "INFO:\n";
        
        switch(opcionElegida)
        {
            case 0:
                modelo.inicializarJuego(nombreJugadores);
                info = "Empieza el jugador: " + modelo.getJugadorActual().getNombre();
                break;
            case 1:
                modelo.jugar();
                info = "Valor del dado: " + modelo.getValorDado() + "\n" + modelo.obtenerCasillaJugadorActual().toString();
                break;
            case 2:
                info = modelo.getCartaActual().toString();
                modelo.aplicarSorpresa();
                break;
            case 3:
                if(modelo.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD))
                    info = modelo.getJugadorActual().getNombre() + " ha salido de la carcel tras pagar la fianza";
                else
                    info = modelo.getJugadorActual().getNombre() + " no ha logrado salir de la carcel pagando la fianza";
                break;
            case 4:
                if(modelo.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO))
                    info = modelo.getJugadorActual().getNombre() + " ha escapado de la carcel";
                else
                    info = modelo.getJugadorActual().getNombre() + " no ha logrado escapar de la carcel";
                break;
            case 5:
                if(modelo.comprarTituloPropiedad())
                    info = modelo.getJugadorActual().getNombre() + " ha comprado un nuevo titulo de propiedad";
                else
                    info = modelo.getJugadorActual().getNombre() + " no ha podido comprar el titulo de propiedad";
                break;
            case 6:
                modelo.hipotecarPropiedad(casillaElegida);
                info = modelo.getJugadorActual().getNombre() + " ha hipotecado la propiedad";
                break;
            case 7:
                if(modelo.cancelarHipoteca(casillaElegida))
                    info = modelo.getJugadorActual().getNombre() + " ha cancelado la hipoteca";
                else
                    info = modelo.getJugadorActual().getNombre() + " no ha podido cancelar la hipoteca";
                break;
            case 8:
                if(modelo.edificarCasa(casillaElegida))
                    info = modelo.getJugadorActual().getNombre() + " ha edificado una casa";
                else
                    info = modelo.getJugadorActual().getNombre() + " no ha podido construir la casa";
                break;
            case 9:
                if(modelo.edificarHotel(casillaElegida))
                    info = modelo.getJugadorActual().getNombre() + " ha edificado un hotel";
                else
                    info = modelo.getJugadorActual().getNombre() + " no ha podido construir el hotel";
                break;
            case 10:
                modelo.venderPropiedad(casillaElegida);
                info = modelo.getJugadorActual().getNombre() + " ha vendido su propiedad";
                break;
            case 11:
                modelo.siguienteJugador();
                info = "Turno de " + modelo.getJugadorActual().getNombre();
                break;
            case 12:
                info = "Fin del juego, obtenemos el ranking";
                System.out.print("\n\tRANKING:\n\n");
                modelo.obtenerRanking();
                System.out.println(modelo.getJugadores().toString());
                System.out.println("\n\n\tEnhorabuena " + modelo.getJugadores().get(0).getNombre() + " has ganado!!");
                System.exit(0);
                break;
            case 13:
                info = "Fin del juego, obtenemos el ranking";
                System.out.print("\n\tRANKING:\n\n");
                modelo.obtenerRanking();
                System.out.println(modelo.getJugadores().toString());
                System.out.println("\n\n\tEnhorabuena " + modelo.getJugadores().get(0).getNombre() + " has ganado!!");
                System.exit(0);
                break;
            case 14:
                info = modelo.getJugadorActual().toString();
                break;
            case 15:
                info = modelo.getJugadores().toString();
                break;
            case 16:
                info = modelo.getTablero().toString();
                break;
        }
        
        return info;
    }
}
