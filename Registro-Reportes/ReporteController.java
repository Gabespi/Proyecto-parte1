
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReporteController {

    
    public static final String[] CATEGORIES = {
        "Baches", "Alumbrado", "Control Animal", "Infraestructura", "Otro"
    };
    
    public ReporteController() {

    }
    
    public String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        return dateFormat.format(new Date());
    }
    
    public boolean validateReport(Reporte report) {

        return report.getTitle() != null && !report.getTitle().isEmpty() &&
               report.getAutor() != null && !report.getAutor().isEmpty() &&
               report.getContenido() != null && !report.getContenido().isEmpty();
    }
    
    public boolean saveReport(Reporte report) {
        return true;
    }
}