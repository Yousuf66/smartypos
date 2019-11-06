/*
 * package ksa.so.web;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import ksa.so.beans.Coordinates;
 * 
 * 
 * @RestController public class MapController {
 * 
 * // List<String> map=new ArrayList<String>(); Coordinates obj = new
 * Coordinates(67.1474958, 24.8871263); int i=0; double longitude[]=
 * {67.1474958,67.1440572,67.1300936,67.1240211,67.1161462,67.0925695,67.0816476
 * ,67.0742554,67.0574809,67.0508719,67.0378069,67.032818,
 * 67.0283789,67.0276815,67.0270378}; double lat[]=
 * {24.8871263,24.887007,24.8871579,24.8867169,24.8842156,24.8729202,24.8659701,
 * 24.8634392,24.8590976,24.858275,24.8549639,24.8510601,24.8537568,24.8543968,
 * 24.8548057};
 * 
 * Coordinates obj1 = new Coordinates(67.0234244, 24.8548548);
 * 
 * @RequestMapping("/location") public Coordinates getLocation() { Coordinates
 * obj = new Coordinates(longitude[i],lat[i]);
 * 
 * i++; if(i>=14) { return obj1; } else { return obj; }
 * 
 * 
 * 
 * }
 * 
 * @RequestMapping(value="/location",method = RequestMethod.POST) public void
 * setLocation(@RequestBody Coordinates cord) {
 * 
 * 
 * 
 * 
 * }
 * 
 * }
 * 
 * 
 */