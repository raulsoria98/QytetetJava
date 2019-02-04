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
public class TituloPropiedad
{
    private String nombre;
    private boolean hipotecada;
    private int precioCompra;
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;
    private int numHoteles;
    private int numCasas;
    private Jugador propietario;
    
    TituloPropiedad(String nombre, int precioCompra, int alquilerBase, float factorRevalorizacion, int hipotecaBase, int precioEdificar)
    {
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;
        this.numHoteles = 0;
        this.numCasas = 0;
        this.hipotecada = false;
        this.propietario = null;
    }
    
    String getNombre()
    {
        return nombre;
    }
    
    Boolean getHipotecada()
    {
        return hipotecada;
    }
    
    int getPrecioCompra()
    {
        return precioCompra;
    }
    
    int getAlquilerBase()
    {
        return alquilerBase;
    }
    
    float getFactorRevalorizacion()
    {
        return factorRevalorizacion;
    }
    
    int getHipotecaBase()
    {
        return hipotecaBase;
    }
    
    int getPrecioEdificar()
    {
        return precioEdificar;
    }
    
    int getNumCasas()
    {
        return numCasas;
    }
    
    int getNumHoteles()
    {
        return numHoteles;
    }
    
    void setHipotecada(Boolean hipoteca)
    {
        hipotecada = hipoteca;
    }

    public Jugador getPropietario()
    {
        return propietario;
    }

    public void setPropietario(Jugador propietario)
    {
        this.propietario = propietario;
    }
    
    int calcularCosteCancelar()
    {
        return (int) (calcularCosteHipotecar() + calcularCosteHipotecar() * 0.1);
    }
    
    int calcularCosteHipotecar()
    {
        return (int) (hipotecaBase + numCasas * 0.5 * hipotecaBase + numHoteles * hipotecaBase);
    }
    
    int calcularImporteAlquiler()
    {
        int costeAlquiler = (int) (alquilerBase + (numCasas * 0.5 + numHoteles * 2));
        
        propietario.modificarSaldo(costeAlquiler);
        
        return costeAlquiler;
    }
    
    int calcularPrecioVenta()
    {
        return (int) ((precioCompra + (numCasas + numHoteles) * precioEdificar) * factorRevalorizacion);
    }
    
    void cancelarHipoteca()
    {
        setHipotecada(false);
    }
    
    void cobrarAlquiler(int coste)
    {
        throw new UnsupportedOperationException("Sin implementar");
    }
    
    void edificarCasa()
    {
        numCasas++;
    }
    
    void edificarHotel()
    {
        numCasas = 0;
        
        numHoteles++;
    }
    
    int hipotecar()
    {
        setHipotecada(true);
        
        int costeHipoteca = calcularCosteHipotecar();
        
        return costeHipoteca;
    }
    
    int pagarAlquiler()
    {
        int costeAlquiler = calcularImporteAlquiler();
        propietario.modificarSaldo(costeAlquiler);
        
        return costeAlquiler;
    }
    
    boolean propietarioEncarcelado()
    {
        return propietario.getEncarcelado();
    }
    
    boolean tengoPropietario()
    {
        return propietario != null;
    }

    @Override
    public String toString()
    {
        return "TituloPropiedad{" + "nombre=" + nombre + ", hipotecada=" + hipotecada + ", precioCompra=" + precioCompra + ", alquilerBase=" + alquilerBase + ", factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase + ", precioEdificar=" + precioEdificar + ", numHoteles=" + numHoteles + ", numCasas=" + numCasas + '}';
    }
}
