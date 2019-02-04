/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author Ray
 */
public class Sorpresa
{
    private String texto;
    private TipoSorpresa tipo;
    private int valor;
    
    /*public Sorpresa()
    {
        texto = "Unknow";
        tipo = TipoSorpresa.SALIRCARCEL;
        valor = 0;
    }*/
    
    Sorpresa(String txt, int value, TipoSorpresa type)
    {
        texto = txt;
        tipo = type;
        valor = value;
    }
    
    String getTexto()
    {
        return texto;
    }
    
    TipoSorpresa getTipo()
    {
        return tipo;
    }
    
    int getValor()
    {
        return valor;
    }
    
    @Override
    public String toString()
    {
        return "Sorpresa{" + "texto=" + texto + ", valor=" + Integer.toString(valor) + ", tipo=" + tipo + "}";
    }
}
