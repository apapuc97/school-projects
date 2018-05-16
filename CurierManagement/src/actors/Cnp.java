package actors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class Cnp {
	private String 		cnp = null;
	private int			gen;
	private int			anul;
	private int			luna;
	private int			ziua;
	private int			judet;
	private int			nnn;
	private int			controlNumber;
	private int			varsta;
	private Calendar	dataNasterii;
	
	public Cnp() {}
	
	public Cnp(String cnp) throws InvalidCnpException {
		validateCnp(cnp);
		this.cnp = cnp;
	}
	public int daysBetween(Calendar d1, Calendar d2){
        return (int)( (d2.getTimeInMillis() - d1.getTimeInMillis()) / (1000 * 60 * 60 * 24));
	}
	
	public void	setCnp(String cnp) throws InvalidCnpException {
		validateCnp(cnp);
		this.cnp = cnp;
		
	}
	
	public int getAge() {
	    Calendar now = Calendar.getInstance();
	    int nowMonth = now.get(Calendar.MONTH) + 1;
	    int nowYear = now.get(Calendar.YEAR);
	    int result = nowYear - anul;

	    if (luna > nowMonth) {
	        result--;
	    }
	    else if (luna == nowMonth) {
	        int nowDay = now.get(Calendar.DAY_OF_MONTH);

	        if (ziua > nowDay) {
	            result--;
	        }
	    }
	    return result;
	}
	
	public void				printAnulNasterii() {
		SimpleDateFormat	dateFormat;
		
		if (cnp == null)
			return;
		setDate();
		dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		
		System.out.println("Data nasterii: " + dateFormat.format(dataNasterii.getTime()));
		System.out.println("Varsta: " + getAge());
	}
	
	public void				setDate() {
		if (cnp == null)
			return;
		dataNasterii = Calendar.getInstance();
		dataNasterii.set(Calendar.DAY_OF_MONTH, ziua);
		dataNasterii.set(Calendar.MONTH, luna);
		dataNasterii.set(Calendar.YEAR, anul);
	}
	
	public static boolean	isLeapYear(int year) {
		if (year % 4 == 0 && year % 100 != 0)
			return true;
		else
			return false;
	}
	
	public static int		getMaxDaysInFebruary(int year) {
		if (isLeapYear(year))
			return 29;
		else
			return 28;
	}
	
	public static int	getControlNumber(String	cnp) {
		int		controlNumber;
		String	controlString;
		int		a;
		int		b;
		int		sum;
		
		controlString = "279146358279";
		sum = 0;
		for (int i = 0; i < 12; i++) {
			a = Integer.parseInt(cnp.charAt(i) + "");
			b = Integer.parseInt(controlString.charAt(i) + "");
			sum += a * b;
		}
		controlNumber = sum % 11;
		if (controlNumber == 10)
			controlNumber = 1;
		return controlNumber;
	}
	
	public void	validateCnp(String cnp) throws InvalidCnpException{

		if (cnp == null)
			throw new InvalidCnpException("CNP nul!");
		if (cnp.length() != 13)
			throw new InvalidCnpException("CNP-ul trebuie sa aiba o lungime de 13 caractere!");
		try {
			Long.parseLong(cnp);
		}
		catch (NumberFormatException e) {
			throw new InvalidCnpException(cnp + " nu reprezinta un numar!");
		}
		gen = Integer.parseInt(cnp.substring(0, 1));
		anul = Integer.parseInt(cnp.substring(1, 3));
		if (gen == 1 || gen == 2 || gen == 7 || gen == 8)
			anul += 1900;
		else if (gen == 3 || gen == 4)
			anul += 1800;
		else if (gen == 5 || gen == 6)
			anul += 2000;
		else 
			throw new InvalidCnpException(gen + " nu corespunde unui gen valid!");
		luna = Integer.parseInt(cnp.substring(3, 5));
		if (luna > 12 || luna <= 0)
			throw new InvalidCnpException(luna + " nu corespunde unei luni valide!");
		ziua = Integer.parseInt(cnp.substring(5, 7));
		if (ziua > 31 || ziua <= 0)
			throw new InvalidCnpException(ziua + " nu corespunde unei zile valide!");
		else if ((luna == 4 || luna == 6 || luna == 9 || luna == 11) && ziua > 30)
			throw new InvalidCnpException(ziua + " nu trebuie sa depaseasca cifra 30!");
		else if (luna == 2 && ziua > getMaxDaysInFebruary(anul))
			throw new InvalidCnpException(ziua + " nu trebuie sa depaseasca cifra " + getMaxDaysInFebruary(anul) + "!");
		judet = Integer.parseInt(cnp.substring(7, 9));
		if (judet <= 0 || judet > 52)
			throw new InvalidCnpException(judet + " nu corespunde unui judet gen valid!");
		nnn = Integer.parseInt(cnp.substring(9, 12));
		if (nnn <= 000)
			throw new InvalidCnpException(nnn + " trebuie sa ia valori intre 001 si 999!");
		controlNumber = Integer.parseInt(cnp.substring(12, 13));
		if (controlNumber != getControlNumber(cnp))
			throw new InvalidCnpException(controlNumber + " nu corespunde unui numar de control valid(" + getControlNumber(cnp) + ")!");
	}
}
