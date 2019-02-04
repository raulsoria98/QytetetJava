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
public abstract class Casilla
{
    private int numeroCasilla;
    private int coste;
    
    Casilla(int numeroCasilla, int coste)
    {
        this.numeroCasilla = numeroCasilla;
        this.coste = coste;
    }

    int getNumeroCasilla()
    {
        return numeroCasilla;
    }

    int getCoste()
    {
        return coste;
    }
    
    public void setCoste(int coste)
    {
        this.coste = coste;
    }

    protected abstract TipoCasilla getTipo();

    protected abstract TituloPropiedad getTitulo();
    
    protected abstract boolean soyEdificable();

    @Override
    public abstract String toString();
}