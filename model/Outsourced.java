package model;

/**
 *
 * @author Livan Martinez
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Constructor
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the CompanyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName the CompanyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
