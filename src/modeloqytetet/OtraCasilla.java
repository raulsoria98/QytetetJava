/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author ray
 */
public class OtraCasilla extends Casilla
{
    private TipoCasilla tipo;
    
    OtraCasilla(int numeroCasilla, int coste, TipoCasilla tipo)
    {
        super(numeroCasilla, coste);
        this.tipo = tipo;
    }
    
    protected TipoCasilla getTipo()
    {
        return tipo;
    }
    
    protected boolean soyEdificable()
    {
        return false;
    }
    
    protected TituloPropiedad getTitulo()
    {
        return null;
    }
    
    @Override
    public String toString()
    {
        return "Casilla{" + "numeroCasilla=" + this.getNumeroCasilla() + ", coste=" + this.getCoste() + ", tipo=" + tipo + "}";
    }
}
