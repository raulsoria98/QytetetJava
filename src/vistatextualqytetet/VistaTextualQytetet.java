/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistatextualqytetet;

import controladorqytetet.ControladorQytetet;
import modeloqytetet.Qytetet;
import controladorqytetet.OpcionMenu;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ray
 */
public class VistaTextualQytetet
{
    ControladorQytetet controlador;
    
    public VistaTextualQytetet()
    {
        controlador = ControladorQytetet.getInstance();
    }
    
    public ArrayList<String> obtenerNombreJugadores()
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
    
    public int elegirCasilla(int opcionMenu)
    {
        int casillaElegida;
        ArrayList<Integer> casillas = controlador.obtenerCasillasValidas(opcionMenu);
        ArrayList<String> lista = new ArrayList<>();
        
        if(casillas.isEmpty())
            casillaElegida = -1;
        else
        {
            for(Integer i : casillas)
            {
                lista.add(i.toString());
            }
            System.out.println(casillas);
            casillaElegida = Integer.parseInt(this.leerValorCorrecto(lista));
        }
        
        return casillaElegida;
    }
    
    public String leerValorCorrecto(ArrayList<String> valoresCorrectos)
    {
        Scanner in = new Scanner (System.in);
        String valor;
        int encontrado;
        
        do{
            System.out.print("Elija una de las opciones anteriores: ");
            valor = in.nextLine();
            
            encontrado = valoresCorrectos.indexOf(valor);
        }while(encontrado == -1);
        
        return valor;
    }
    
    public int elegirOperacion()
    {
        ArrayList<Integer> operaciones = controlador.obtenerOperacionesJuegoValidas();
        ArrayList<String> lista = new ArrayList<>();
        
        for(Integer i : operaciones)
        {
            lista.add(i.toString());
            System.out.println(i + ")\t" + OpcionMenu.values()[i]);
        }
        
        return Integer.parseInt(this.leerValorCorrecto(lista));
    }
    
    public void main(String args[])
    {
        VistaTextualQytetet ui = new VistaTextualQytetet();
        controlador.setNombreJugadores(ui.obtenerNombreJugadores());
        int operacionElegida, casillaElegida = 0;
        boolean necesitaElegirCasilla;
        
        while(true)
        {
            operacionElegida = ui.elegirOperacion();
            necesitaElegirCasilla = controlador.necesitaElegirCasilla(operacionElegida);
            
            if(necesitaElegirCasilla)
                casillaElegida = ui.elegirCasilla(operacionElegida);
            if(!necesitaElegirCasilla || casillaElegida >= 0)
                System.out.println(controlador.realizarOperacion(operacionElegida, casillaElegida));
        }
    }
}