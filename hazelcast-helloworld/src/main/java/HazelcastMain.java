/**
 * 
 */

import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author Vipin Rawat
 *
 */
public class HazelcastMain {
	public static void main(String[] args) {
 HazelcastInstance hz1 = Hazelcast.newHazelcastInstance();
 HazelcastInstance hz2 = Hazelcast.newHazelcastInstance();
 
 Map<Integer, String> map = hz1.getMap("SessionMap");
 map.put(1, "Google");
 map.put(2, "Apple");
 map.put(3, "Samsung");
 map.put(4, "TCS");
 map.put(5, "Softtek");
 
 System.out.println("Hazlecast Nodes in this cluster "+Hazelcast.getAllHazelcastInstances().size());
 hz1.shutdown();
 System.out.println("Hazelcast nodes in this cluster after shutdown "+Hazelcast.getAllHazelcastInstances().size());
 Map<Integer,String> mapRestored = hz2.getMap("SessionMap");
 for(String val:mapRestored.values())
 {
	 System.out.println("-"+val);
 }
 }
}