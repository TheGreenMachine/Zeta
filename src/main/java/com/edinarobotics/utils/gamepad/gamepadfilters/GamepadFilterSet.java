package com.edinarobotics.utils.gamepad.gamepadfilters;

import java.util.List;

import com.edinarobotics.utils.gamepad.GamepadAxisState;

/**
 * This class applies multiple other GamepadFilters to a Gamepad.
 */
public class GamepadFilterSet implements GamepadFilter{
    private List<GamepadFilter> filters;
    
    /**
     * Constructs a new GamepadFilterSet that applies the given filters to the
     * gamepad in the order in which they are positioned in the array.
     * @param filters The array of filters to be applied to the gamepad.
     */
    public GamepadFilterSet(List<GamepadFilter> filters){
        this.filters = filters;
    }
    
    /**
     * Filters the given GamepadAxisState object through the given set of
     * filters. The filters are applied in order.
     * @param toFilter The GamepadAxisState object to be filtered.
     * @return A new GamepadAxisState object representing the result of
     * filtering the input GamepadAxisState object.
     */
    public GamepadAxisState filter(GamepadAxisState toFilter){
        GamepadAxisState current = toFilter;
        
    	for (GamepadFilter filter : filters){
    		current = filter.filter(current);
    	}
    	
        return current;
        
    }
}
