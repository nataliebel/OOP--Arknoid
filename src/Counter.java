/**
 * The type Counter.
 */
public class Counter {

    private int currentNumber;


    /**
     * Instantiates a new Counter.
     *
     * @param number the number
     */
    public Counter(int number) {
        this.currentNumber = number;
    }


    /**
     * Increase.
     *
     * @param number the number
     */
    public void increase(int number) {
        this.currentNumber = this.getValue() + number;
    }


    /**
     * Decrease.
     *
     * @param number the number
     */
    public void decrease(int number) {
        this.currentNumber = this.getValue() - number;
    }


    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return this.currentNumber;
    }
}