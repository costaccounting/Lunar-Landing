

/**
 * Class to calculate the physics of a lunar lander descending in the moon's gravity.
 * This is a very simple one-dimensional simulation, there is no attempt to calculate orbital dynamics!
 */
public class PhysicsEngine {
    public static final double GRAVITY = -1.622;       // The acceleration due to gravity on the moon in meters per second per second (m/s/s), negative means down
    public static final double THRUST_STRENGTH = 5.0;  // The strength of the lander's rocket in meters per second per second (m/s/s)
    public static final double INITIAL_HEIGHT = 500;   // The initial height in meters (m). The real Apollo 11 lunar module started powered descent at height 11853 m.
    public static final double SAFE_LANDING_SPEED = 5.0;   // The safe landing speed below which we don't crash in meters per second (m/s)
    public static final double TIME_STEP = 0.1;        // The size of each step in the simulation, in seconds (s)

    private double _height;       // The lander's current height in meters, height of zero means the simulation is stopped
    private double _vel;          // The lander's current velocity in meters per second, negative means moving down
    private double _fuel;         // The amount of fuel remaining as a percent, where 100 means full and 0 means empty
    private double _elapsedTime;  // The simulation elapsed time in seconds
    
    private boolean _activated = false; //the state of rocket engine. 
    
    private boolean _thrust = false; //the state of rocket thruster. 
    
    

    /**
     * Getter for height.
     * @return The lander's height in meters (m).
     */
    public double getHeight() {
        return _height;
    }

    /**
     * Getter for velocity.
     * @return The lander's velocity in meters per second (m/s), where positive means up and negative means down.
     */
    public double getVelocity() {
        return _vel;
    }

    /**
     * Getter for fuel amount.
     * @return The amount of fuel remaining as a percent.
     */
    public double getFuel() {
        return _fuel;
    }

    /**
     * Getter for elapsed time.
     * @return The simulation elapsed time in seconds.
     */
    public double getElapsedTime() {
        return _elapsedTime;
    }


    /**
     * Starts the simulation by setting initial conditions.
     */
    public void start() {
        _elapsedTime = 0;
        _vel = 0;
        _height = INITIAL_HEIGHT;
        _fuel = 100;
        active();
    }

    /**
     * Calculates the lander's height and velocity for the next step in the simulation.
     * In each step this method updates
     * - The lunar lander's velocity, based on engine thrust and gravity
     * - The lander's height, based on velocity
     * - The lander's fuel amount, based on thrust
     * @param thrust true means the lander's rocket is firing, false means not firing.
     * @return true if the simulation is finished (lander has landed), false if simulation is still running.
     */
    public boolean nextStep(boolean thrust) {
        boolean result = false;      // The value to return from this method, false unless set otherwise
        
        // Return immediately if simulation is already stopped
        if (_height <= 0) {
            return result;
        }

        // If there is fuel left then apply thrust from the engine and decrease the fuel amount
        double actualThrust = 0;
        if (_fuel > 0 && thrust) {
            actualThrust = THRUST_STRENGTH;
            _fuel -= actualThrust/7.5;    // Decrease the amount of fuel, engine is thrusting
            if (_fuel < 0) {
                _fuel = 0;         // Make sure fuel amount doesn't go negative
            }
        }

        // Update the lunar lander's velocity and height. Also update the simulation clock (elapsed time).
        _vel += (GRAVITY + actualThrust) * TIME_STEP;
        _height += _vel * TIME_STEP;
        _elapsedTime += TIME_STEP;

        // Stop the simulation when height becomes zero or negative
        if (_height <= 0) {
            _height = 0;        // Make sure height does not go negative
            result = true;
        }

        return result;
    }
    /**
     * checks the initial height of rocket at the start
     * @return the value of initial height. 
     */
    public double getIntialHeight(){
        return INITIAL_HEIGHT;
    }
    /**
     * sets the value of rocket to active
     */
    public void active(){
        this._activated = true;
    }
    /**
     * checks if rocket is active
     * @return value of whther the rocket is active
     */
    public boolean isActive(){
        return this._activated;
    }
    /**
     * sets the value of thruster to active
     */
    public void thruster_active(){
        this._thrust = true;
    }
    /**
     * sets the value of thruster to inactive
     */
    public void thruster_inactive(){
        this._thrust = false;
    }
    /**
     * checks if thruster is active
     * @return value of whether the rocket is active
     */
    public boolean isThrusterActive(){
        return this._thrust;
    }

}
