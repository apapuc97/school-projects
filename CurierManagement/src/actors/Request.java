package actors;

import java.util.HashMap;

public class Request {
	public enum	Cities {
		Bucuresti,
		Cluj,
		Iasi,
		Constanta,
		Craiova,
		Brasov,
		Galati,
		Ploiesti,
		Oradea,
		Braila,
		Arad,
		Pitesti,
		Sibiu,
		Bacau,
		Timisoara
	}
	
	private int		id;
	private Client	sender;
	private Client	receiver;
	private Cities	city;
	private	float	weight;
	private int		specialCondition;
	private	float	price;
	private String	status;
	private int		driver_id;
	private static HashMap<Cities, Integer>		distances = null;
	
	public Request() {
		if (distances == null) {
			distances = new HashMap<Cities, Integer>();
			distances.put(Cities.Bucuresti, 541);
			distances.put(Cities.Cluj, 315);
			distances.put(Cities.Iasi, 634);
			distances.put(Cities.Constanta, 764);
			distances.put(Cities.Craiova, 339);
			distances.put(Cities.Brasov, 412);
			distances.put(Cities.Galati, 677);
			distances.put(Cities.Ploiesti, 518);
			distances.put(Cities.Oradea, 170);
			distances.put(Cities.Braila, 679);
			distances.put(Cities.Arad, 53);
			distances.put(Cities.Pitesti, 425); // 5h 30m
			distances.put(Cities.Sibiu, 269); // 3h 7m
			distances.put(Cities.Bacau, 588);
		}
		this.id = 0;
		this.sender = new Client();
		this.receiver = new Client();
		this.city = Cities.Bucuresti;
		this.weight = 0;
		this.specialCondition = 0;
		this.price = 0;
		this.status = "no name";
	}
	
	public int	getDistance() {
		Integer		distance;
		
		distance = distances.get(city);
		if (distance != null)
			return distance;
		return 0;
	}
	
	public int getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}
	public void setDriver_id(String driver_id) {
		int	id;
		
		try {
			id = Integer.parseInt(driver_id);
			this.driver_id = id;
		} catch (NumberFormatException e) {
			this.driver_id = 0;
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Client getSender() {
		return sender;
	}	
	public void setSender(Client sender) {
		if (sender != null)
			this.sender = sender;
	}
	public Client getReceiver() {
		return receiver;
	}
	public void setReceiver(Client receiver) {
		if (receiver != null)
			this.receiver = receiver;
	}
	public Cities getCity() {
		return city;
	}
	public void setCity(Cities city) {
		if (city != null)
			this.city = city;
	}
	public void setCity(String city) {
		if (city == null) {
			return;
		}
		try {
			this.city = Cities.valueOf(city);
		} catch (IllegalArgumentException e) {
			this.city = null;
		}
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public int getSpecialCondition() {
		return specialCondition;
	}
	public void setSpecialCondition(int specialCondition) {
		this.specialCondition = specialCondition;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		if (status != null)
			this.status = status;
	}
}
