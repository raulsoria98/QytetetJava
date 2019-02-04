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
public class Especulador extends Jugador
{
    private int fianza;
    private int factor;
    
    protected Especulador(Jugador jugador, int fianza)
    {
        super(jugador);
        
        this.fianza = fianza;
        this.factor = 2;
    }
    
    protected void pagarImpuesto(int cantidad)
    {
        this.modificarSaldo(-cantidad / factor);
    }
    
    @Override
    protected Especulador convertirme(int fianza)
    {
        return this;
    }
    
    @Override
    protected boolean deboIrACarcel()
    {
        if(super.deboIrACarcel())
            return !pagarFianza();
        else
            return false;
    }
    
    private boolean pagarFianza()
    {
        if(this.getSaldo() >= fianza)
        {
            this.modificarSaldo(-fianza);
            return true;
        }
        else
            return false;
    }
    
    @Override
    protected boolean puedoEdificarCasa(TituloPropiedad titulo)
    {
        return titulo.getNumCasas() < 8 && titulo.getPropietario() == this && this.getSaldo() >= titulo.getPrecioEdificar();
    }
    
    @Override
    protected boolean puedoEdificarHotel(TituloPropiedad titulo)
    {
        return titulo.getNumCasas() == 4 && titulo.getNumHoteles() < 8 && titulo.getPropietario() == this && this.getSaldo() >= titulo.getPrecioEdificar();
    }
    
    @Override
    public String toString()
    {
        String salida = "Especulador:\n";
        
        salida += super.toString();
        
        salida += "fianza = " + fianza + "\n";
        
        return salida;
    }
}
