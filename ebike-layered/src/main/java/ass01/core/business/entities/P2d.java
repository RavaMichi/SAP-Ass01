package ass01.core.business.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * 2-dimensional point
 * objects are completely state-less
 *
 */
public class P2d implements java.io.Serializable {

    private double x,y;

    @JsonCreator
    public P2d(@JsonProperty("x") double x, @JsonProperty("y") double y){
        this.x=x;
        this.y=y;
    }

    public double x() {
    	return x;
    }
    
    public double y() {
    	return y;
    }
    
    public P2d sum(V2d v){
        return new P2d(x+v.x(),y+v.y());
    }

    public V2d sub(P2d v){
        return new V2d(x-v.x(),y-v.y());
    }
    
    public String toString(){
        return "P2d("+x+","+y+")";
    }

}
