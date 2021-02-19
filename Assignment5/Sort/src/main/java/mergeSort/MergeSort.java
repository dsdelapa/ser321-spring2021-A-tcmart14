package mergeSort;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MergeSort {
  /**
   * Thread that declares the lambda and then initiates the work
   */

  public static int message_id = 0;

  public static JSONObject init(int[] array) {
    JSONArray arr = new JSONArray();
    for (var i : array) {
      arr.put(i);
    }
    JSONObject req = new JSONObject();
    req.put("method", "init");
    req.put("data", arr);
    return req;
  }

  public static JSONObject peek() {
    JSONObject req = new JSONObject();
    req.put("method", "peek");
    return req;
  }

  public static JSONObject remove() {
    JSONObject req = new JSONObject();
    req.put("method", "remove");
    return req;
  }
  
  public static void Test(int port, String host) {
    int[] a = {539,975,489,552,37,669,737,32,19,66,737,626,923,246,496,287,245,124,575,194,26,299,449,829,685,299,966,450,76,389,269,339,986,801,699,779,930,223,536,573,240,987,604,101,271,413,996,140,126,180,729,58,210,269,914,764,813,370,855,811,130,205,341,691,608,983,524,907,635,396,408,495,360,886,256,39,835,934,664,700,690,528,610,500,589,249,724,212,402,453,99,404,481,92,172,873,739,10,133,267,629,689,560,31,423,161,478,76,279,729,574,118,192,968,96,528,629,26,547,70,326,339,553,98,712,63,562,640,278,964,771,499,665,181,619,572,809,462,664,699,943,729,3,206,705,122,340,777,613,464,314,778,725,724,47,403,850,194,13,489,461,466,517,808,662,904,633,675,877,224,304,559,267,402,46,142,871,23,650,409,607,165,233,990,22,771,673,666,268,865,230,357,318,247,72,391,442,559,315,373,938,120,578,627,285,659,105,501,464,134,345,180,562,385,231,898,747,704,733,585,825,351,214,80,452,270,333,679,301,755,46,654,846,268,978,811,514,111,532,686,308,926,477,952,330,402,947,396,21,976,749,262,30,508,23,725,856,21,111,626,916,755,628,408,900,462,942,88,557,300,161,410,327,586,332,99,321,223,813,728,427,818,564,871,229,81,283,712,571,111,132,477,667,515,654,750,191,312,782,538,483,937,596,825,320,706,722,201,658,991,708,897,638,24,711,199,763,753,888,173,612,896,641,516,111,165,104,699,799,92,691,586,56,437,693,694,237,347,138,529,81,57,344,795,359,730,703,538,774,559,488,225,193,750,487,839,419,862,897,763,19,342,673,4,278,655,95,984,573,612,699,118,679,914,617,115,820,945,68,192,614,666,971,528,295,324,618,575,565,575,112,184,385,332,463,259,236,936,520,156,2,120,78,956,555,822,680,37,864,853,970,937,604,773,851,920,752,274,262,745,946,970,102,131,119,23,805,118,22,167,636,748,306,827,419,545,304,712,422,950,40,458,48,605,228,996,133,141,849,879,401,480,78,981,386,607,218,646,351,559,25,825,291,828,68,934,753,867,299,63,210,368,376,52,424,904,805,946,365,543,114,187,67,429,245,535,843,192,490,163,906,656,715,99,711,128,125,756,302,565};
    JSONObject response = NetworkUtils.send(host, port, init(a));
    
    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);
    long startTime = System.nanoTime();
    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;
 
      } else{
        break;
      }
    }
    long endTime = System.nanoTime();
    long time = (endTime - startTime) / 1000000;
    System.out.println("Total elements: " + a.length);
    System.out.println("\nTotal Time: " + time);
  }

  public static void main(String[] args) {
    
    // use the port of one of the branches to test things
    Test(Integer.valueOf(args[0]), args[1]);

  }
}
