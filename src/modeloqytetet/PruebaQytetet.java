/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;
import java.util.Scanner;
import vistatextualqytetet.VistaTextualQytetet;

/**
 *
 * @author Ray
 */
public class PruebaQytetet
{
    static Qytetet juego = Qytetet.getInstance();
    
    private static ArrayList<Sorpresa> metodo1()
    {
        ArrayList<Sorpresa> aux = new ArrayList<>();
        
        for(Sorpresa carta: juego.getMazo())
        {
            if(carta.getValor() > 0)
                aux.add(carta);
        }
        
        return aux;
    }
    
    private static ArrayList<Sorpresa> metodo2()
    {
        ArrayList<Sorpresa> aux = new ArrayList<>();
        
        for(Sorpresa carta: juego.getMazo())
        {
            if(carta.getTipo() == TipoSorpresa.IRACASILLA)
                aux.add(carta);
        }
        
        return aux;
    }
    
    private static ArrayList<Sorpresa> metodo3(TipoSorpresa tipo)
    {
        ArrayList<Sorpresa> aux = new ArrayList<>();
        
        for(Sorpresa carta: juego.getMazo())
        {
            if(carta.getTipo() == tipo)
                aux.add(carta);
        }
        
        return aux;
    }
    
    private static void practica1_1()
    {
        System.out.println("\tMETODO1");
        
        for(Sorpresa t: metodo1())
            System.out.println(t.toString());
        
        System.out.println("\n\tMETODO2");
        
        for(Sorpresa t: metodo2())
            System.out.println(t.toString());
        
        System.out.println("\n\tMETODO3");
        
        for(TipoSorpresa t: TipoSorpresa.values())
            for(Sorpresa k: metodo3(t))
                System.out.println(k.toString());
    }
    
    private static void practica1_2()
    {
        System.out.println(juego.getTablero().toString());
    }
    
    private static void practica2()
    {
        ArrayList<String> names = getNombreJugadores();
        
        for(String t: names)
            System.out.println("\n" + t);
        
        juego.inicializarJuego(names);
        
        System.out.println(juego.toString());
    }
    
    private static void practica3()
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Ray");
        names.add("Juse");
        juego.inicializarJuego(names);
        
        Scanner in = new Scanner (System.in);
        
        System.out.print("\n1./ Probar método mover y comprar\n" +
                "2./ Probar método pagarAlquiler\n" +
                "3./ Probar método aplicarSorpresa\n" +
                "4./ Pruebas de gestión inmbobiliaria\n" +
                "5./ Prueba de salir de la carcel\n" +
                "6./ Prueba del ranking tras jugar un rato\n" +
                "\nElija una de las opciones anteriores: ");
        
        int opcion = in.nextInt(); in.skip("\n");
        switch(opcion)
        {
            case 1:
                System.out.print("\nCasilla actual del jugador actual: ");
                System.out.println(juego.getJugadorActual().getCasillaActual());
                System.out.print("\nCasilla del jugador actual tras moverse a la casilla nº 3 (IMPUESTO): ");
                juego.mover(3);
                System.out.println(juego.getJugadorActual().getCasillaActual());
                System.out.print("\nEstado del juego: ");
                System.out.println(juego.getEstadoJuego());
                System.out.print("\nCasilla del jugador actual tras moverse a la casilla nº 7 (CALLE): ");
                juego.mover(7);
                System.out.println(juego.getJugadorActual().getCasillaActual());
                System.out.print("\nEstado del juego: ");
                System.out.println(juego.getEstadoJuego());
                System.out.println("\nJugador compra casilla actual...");
                juego.comprarTituloPropiedad();
                System.out.print("\nEstado del juego: ");
                System.out.println(juego.getEstadoJuego());
                System.out.println("\nPropiedades del jugador actual:");
                System.out.println(juego.getJugadorActual().getPropiedades());
                System.out.print("\nCasilla del jugador actual tras moverse a la casilla nº 13 (SORPRESA): ");
                juego.mover(13);
                System.out.println(juego.getJugadorActual().getCasillaActual());
                System.out.print("\nEstado del juego: ");
                System.out.println(juego.getEstadoJuego());
                break;
            case 2:
                System.out.println("\nJugador actual: " + juego.getJugadorActual().getNombre());
                juego.mover(19);
                System.out.print("\nCasilla del jugador " + juego.getJugadorActual().getNombre() + " tras moverse a la casilla nº 19 (CALLE): ");
                System.out.println(juego.getJugadorActual().getCasillaActual());
                System.out.println("\n" + juego.getJugadorActual().getNombre() + " compra casilla actual...");
                juego.comprarTituloPropiedad();
                System.out.print("\nSaldo de " + juego.getJugadorActual().getNombre() + ": " + juego.getJugadorActual().getSaldo());
                System.out.print("\nLe toca jugar al siguiente jugador...");
                juego.siguienteJugador();
                System.out.println("\nJugador actual: " + juego.getJugadorActual().getNombre());
                System.out.print("\nSaldo de " + juego.getJugadorActual().getNombre() + ": " + juego.getJugadorActual().getSaldo());
                juego.mover(19);
                System.out.print("\nCasilla del jugador " + juego.getJugadorActual().getNombre() + " tras moverse a la casilla nº 19 (CALLE): ");
                System.out.println(juego.getJugadorActual().getCasillaActual());
                System.out.println("\n" + juego.getJugadorActual().getNombre() + " paga el alquiler...");
                juego.getJugadorActual().pagarAlquiler();
                System.out.print("\nSaldo de " + juego.getJugadorActual().getNombre() + ": " + juego.getJugadorActual().getSaldo());
                juego.siguienteJugador();
                System.out.println("\nSaldo de " + juego.getJugadorActual().getNombre() + ": " + juego.getJugadorActual().getSaldo());
                break;
            case 3:
                System.out.println("\nVamos a recorrer todo el mazo, aplicando así todas las sorpresas: ");
                for(int i = 0; i < Qytetet.NUM_SORPRESAS; i++)
                {
                    juego.mover(8);
                    System.out.print("\nSORPRESA Nº: " + i + " tipo: " + juego.getCartaActual().getTipo());
                    juego.aplicarSorpresa();
                    System.out.print("\nEstado del juego: ");
                    System.out.println(juego.getEstadoJuego());                    
                }
                break;
            case 4:
                /*System.out.print("\nCasilla del jugador actual tras moverse a la casilla nº 4 (CALLE): ");
                juego.mover(4);
                System.out.println(juego.getJugadorActual().getCasillaActual());
                System.out.print("\nJugador actual compra propiedad...");
                juego.comprarTituloPropiedad();
                System.out.println("\nPropiedades del jugador actual:");
                System.out.println(juego.getJugadorActual().getPropiedades());
                System.out.print("\nEstado del juego: ");
                System.out.println(juego.getEstadoJuego());
                System.out.println("\nJugador hipoteca su propiedad...");
                System.out.print("\nEstado del juego: ");
                System.out.println(juego.getEstadoJuego());*/
                System.out.print("\nMoviendo jugador a casilla 4 de calle y comprandola\n");             
                juego.mover(4);
                System.out.println(juego.getJugadorActual().getCasillaActual().getTipo());
                System.out.println(juego.getEstadoJuego()); 
                System.out.print("Saldo jugador = "+ juego.getJugadorActual().getSaldo() + "\n");
                System.out.println("\nEdificamos 3 casas...");  
                juego.comprarTituloPropiedad();
                juego.edificarCasa(4);
                juego.edificarCasa(4);
                juego.edificarCasa(4);
                System.out.println("Numero de casas: " + juego.getJugadorActual().getPropiedades().get(0).getNumCasas()) ;
                System.out.println("\nEdificamos otra casa y construimos un hotel...");  
                juego.edificarCasa(4);
                juego.edificarHotel(4);
                System.out.println("Numero de casas: " + juego.getJugadorActual().getPropiedades().get(0).getNumCasas()) ;
                System.out.println("Numero de hoteles: " + juego.getJugadorActual().getPropiedades().get(0).getNumHoteles()) ;
                System.out.print("Saldo jugador = "+ juego.getJugadorActual().getSaldo() + "\n");
                System.out.println("\nHipotecar\n");
                juego.hipotecarPropiedad(4);
                System.out.println("hipotecado :" + juego.getJugadorActual().getCasillaActual().getTitulo().getHipotecada());
                System.out.print("Saldo jugador = "+ juego.getJugadorActual().getSaldo() + "\n");  

                System.out.println("\nCancelando hipoteca");
                juego.cancelarHipoteca(4);
                System.out.println("hipotecado :" + juego.getJugadorActual().getCasillaActual().getTitulo().getHipotecada());
                System.out.print("Saldo jugador = "+ juego.getJugadorActual().getSaldo() + "\n");

                System.out.println("\nVenta propiedad");
                juego.venderPropiedad(4);
                System.out.println("propietario" +juego.getJugadorActual().getCasillaActual().getTitulo().tengoPropietario());
                System.out.print("Saldo jugador = "+ juego.getJugadorActual().getSaldo() + "\n");
                break;
            case 5:
                System.out.print("\nCasilla del jugador actual tras moverse a la casilla nº 15 (JUEZ): ");
                juego.mover(15);
                System.out.println(juego.getJugadorActual().getCasillaActual());
                System.out.print("\nEstado del juego: ");
                System.out.println(juego.getEstadoJuego());
                System.out.print("\nJugador intenta salir tirando dado...");
                juego.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
                System.out.print("\nEstado del juego: ");
                System.out.println(juego.getEstadoJuego());
                System.out.print("\nCasilla del jugador actual tras moverse a la casilla nº 15 (JUEZ): ");
                juego.mover(15);
                System.out.println(juego.getJugadorActual().getCasillaActual());
                System.out.print("\nEstado del juego: ");
                System.out.println(juego.getEstadoJuego());
                System.out.print("\nJugador intenta salir pagando...");
                juego.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
                System.out.print("\nEstado del juego: ");
                System.out.println(juego.getEstadoJuego());
                break;
            case 6:
                for(int i = 0; i < 30; i++)
                {
                    juego.jugar();
                    juego.siguienteJugador();
                }
                juego.mover(3);
                juego.siguienteJugador();
                juego.mover(1);
                juego.comprarTituloPropiedad();
                juego.siguienteJugador();
                juego.mover(6);
                juego.comprarTituloPropiedad();
                juego.siguienteJugador();
                juego.mover(4);
                juego.comprarTituloPropiedad();
                juego.siguienteJugador();
                juego.mover(8);
                juego.aplicarSorpresa();
                juego.siguienteJugador();
                System.out.print("\n\tRANKING:\n\n");
                juego.obtenerRanking();
                for(Jugador j: juego.getJugadores())
                    System.out.println(j);
                break;
        }
    }
    
    public static ArrayList<String> getNombreJugadores()
    {
        int numJugadores;
        String name;
        Scanner in = new Scanner (System.in);
        ArrayList<String> nombres = new ArrayList<>();
        
        System.out.print("\nIntroduzca el nº de jugadores: ");
        numJugadores = in.nextInt(); in.skip("\n");
        System.out.println();
        
        assert numJugadores > Qytetet.MAX_JUGADORES || numJugadores < Qytetet.MIN_JUGADORES;
        
        if(numJugadores <= Qytetet.MAX_JUGADORES && numJugadores >= Qytetet.MIN_JUGADORES)
        {
            for(int i = 0; i < numJugadores; i++)
            {
                System.out.print("Introduzca el nombre del jugador " + (i+1) + ": ");
                name = in.nextLine();
                nombres.add(name);
            }
        }
        else
        {
            System.err.println("Número de jugadores incorrecto. Qytetet (2-4 jugadores)");
            System.exit(0);
        }
        
        return nombres;
    }
    
    public static void main(String[] args)
    {   
        VistaTextualQytetet prueba = new VistaTextualQytetet();
        
        prueba.main(args);
    }
}