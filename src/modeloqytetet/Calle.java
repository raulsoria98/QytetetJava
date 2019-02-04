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
public class Calle extends Casilla
{
    private TituloPropiedad titulo;
    
    Calle(TituloPropiedad titulo, int numeroCasilla)
    {
        super(numeroCasilla, titulo.getPrecioCompra());
        this.titulo = titulo;
    }
    
    public TituloPropiedad asignarPropietario(Jugador jugador)
    {
        titulo.setPropietario(jugador);
        
        return titulo;
    }
    
    @Override
    protected TipoCasilla getTipo()
    {
        return TipoCasilla.CALLE;
    }
    
    @Override
    protected TituloPropiedad getTitulo()
    {
        return titulo;
    }
    
    public int pagarAlquiler()
    {
        return titulo.pagarAlquiler();
    }
    
    private void setTitulo(TituloPropiedad titulo)
    {
        this.titulo = titulo;
    }
    
    protected boolean soyEdificable()
    {
        return true;
    }
    
    public boolean tengoPropietario()
    {
        return titulo.tengoPropietario();
    }
    
    @Override
    public String toString()
    {
        return "Casilla{" + "numeroCasilla=" + this.getNumeroCasilla() + ", coste=" + this.getCoste() + ", tipo=" + TipoCasilla.CALLE + ", titulo=" + titulo + "}";
    }
}
