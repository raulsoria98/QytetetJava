/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;

/**
 *
 * @author Ray
 */
public class Tablero
{
    private ArrayList<Casilla> casillas;
    private Casilla carcel;

    Tablero()
    {
        this.inicializar();
    }

    ArrayList<Casilla> getCasillas()
    {
        return casillas;
    }

    Casilla getCarcel()
    {
        return carcel;
    }

    @Override
    public String toString()
    {
        String salida = "TABLERO\ncasillas:";
        for (Casilla c: casillas)
            salida += "\n" + c.toString();
        salida = salida + "\n\ncarcel=" + carcel.toString();
        salida = salida + "\n";
        return salida;
    }
    
    private void inicializar()
    {
        casillas = new ArrayList<>();
        
        casillas.add(new OtraCasilla(0, 0, TipoCasilla.SALIDA));
        casillas.add(new OtraCasilla(3, 700, TipoCasilla.IMPUESTO));
        casillas.add(new OtraCasilla(5, 0, TipoCasilla.CARCEL));
        casillas.add(new OtraCasilla(8, 0, TipoCasilla.SORPRESA));
        casillas.add(new OtraCasilla(10, 0, TipoCasilla.PARKING));
        casillas.add(new OtraCasilla(13, 0, TipoCasilla.SORPRESA));
        casillas.add(new OtraCasilla(15, 0, TipoCasilla.JUEZ));
        casillas.add(new OtraCasilla(18, 0, TipoCasilla.SORPRESA));
        
        int[] excepciones = {0, 3, 5, 8, 10, 13, 15, 18};
        int contador = 0,
            j = 0;
        
        ArrayList<TituloPropiedad> titulos = new ArrayList<>();
        
        titulos.add(new TituloPropiedad("Rivia", 500, 50, 0.8f, 150, 250));
        titulos.add(new TituloPropiedad("Lyria", 525, 53, 0.8f, 200, 275));
        titulos.add(new TituloPropiedad("Mahakam", 550, 57, 0.9f, 250, 300));
        titulos.add(new TituloPropiedad("Vengerberg", 600, 61, 0.9f, 300, 325));
        titulos.add(new TituloPropiedad("Kaer Morhen", 625, 65, 1f, 350, 350));
        titulos.add(new TituloPropiedad("Maribor", 650, 69, 1f, 400, 400));
        titulos.add(new TituloPropiedad("Cintra", 700, 73, 1.1f, 450, 450));
        titulos.add(new TituloPropiedad("Skellige", 750, 77, 1.1f, 500, 500));
        titulos.add(new TituloPropiedad("Brokilón", 800, 81, 1.1f, 600, 550));
        titulos.add(new TituloPropiedad("Novigrado", 850, 85, 1.2f, 700, 600));
        titulos.add(new TituloPropiedad("Wyzima", 900, 89, 1.2f, 850, 650));
        titulos.add(new TituloPropiedad("Nilfgaard", 1000, 100, 1.2f, 950, 750));
        
        for(int i = 0; i < Qytetet.NUM_CASILLAS; i++)
        {
            if(contador < excepciones.length && excepciones[contador] == i)
                contador++;
            else
            {
                casillas.add(i, new Calle(titulos.get(j), i));
                j++;
            }
        }
        
        carcel = casillas.get(5);
    }
    
    boolean esCasillaCarcel(int numeroCasilla)
    {
        return numeroCasilla == getCarcel().getNumeroCasilla();
    }
    
    Casilla obtenerCasillaFinal(Casilla casilla, int desplazamiento)
    {
        int end = (casilla.getNumeroCasilla() + desplazamiento) % Qytetet.NUM_CASILLAS;
        
        return getCasillas().get(end);
    }
    
    Casilla obtenerCasillaNumero(int numeroCasilla)
    {
        return getCasillas().get(numeroCasilla);
    }
}