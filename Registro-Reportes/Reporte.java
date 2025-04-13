import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporte {
    private String numFolio;
    private String idReporte;
    private String title;
    private String autor;
    private String categoria;
    private String contenido;
    private String date;
    private String archivoAdjunto;
    
    public Reporte() {
        this.numFolio = "";
        this.idReporte = "";
        this.title = "";
        this.autor = "";
        this.categoria = "";
        this.contenido = "";
        this.archivoAdjunto = "";
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    
    public Reporte(String numFolio, String idReporte, String title, String autor, String categoria, String contenido, String date, String archivoAdjunto) {
        this.numFolio = numFolio;
        this.idReporte = idReporte;
        this.title = title;
        this.autor = autor;
        this.categoria = categoria;
        this.contenido = contenido;
        this.date = date;
        this.archivoAdjunto = archivoAdjunto;
    }

    public String getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(String idReporte) {
        this.idReporte = idReporte;
    }
    public String getArchivoAdjunto() {
        return archivoAdjunto;
    }
    public void setArchivoAdjunto(String archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }
    public String getNumFolio() {
        return numFolio;
    }

    public void setNumFolio(String numFolio) {
        this.numFolio = numFolio;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String author) {
        this.autor = author;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return """
               Reporte Registrado con Éxito!
               ID Reporte: %s
               Número de Folio: %s
               Titulo: %s
               Autor: %s
               Categoria: %s
               Archivo Adjunto: %s
               """.formatted(idReporte, numFolio, title, autor, categoria, archivoAdjunto);
    }
}