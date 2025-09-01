import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpiryDate implements Module{

    private Date expiryDate;
    private boolean valid = false;
    public ExpiryDate(String dateee)  {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
            this.expiryDate = sdf.parse(dateee);
            valid = true;
        } catch (ParseException e){
            valid = false;
        }

    }
    @Override
    public boolean isValid() {
        return valid;
    }
    public Date getExpiryDate(){
        return expiryDate;
    }
}
