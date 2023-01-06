package model;

/**
 *
 * @author Livan Martinez
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * Constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the MachineID
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the MachineID to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}