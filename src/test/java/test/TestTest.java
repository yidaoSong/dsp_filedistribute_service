package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

public class TestTest {
public static void main(String[] args) {
	List<Map> maplist = new ArrayList<Map>();
	
	
    for(int i = 0;i<50;i++){
    	Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("id", i);pmap.put("pid", 0);pmap.put("name", i);
		maplist.add(pmap);
    	for(int j=0;j<80;j++){
    		Map<String, Object> map = new HashMap<String, Object>();
    		String id = Integer.toString(i)+"."+Integer.toString(j);
    		String name = Integer.toString(i)+"_"+Integer.toString(j);
    		map.put("id", id);map.put("pid", i);map.put("name", name);
    		maplist.add(map);
    	}
    }  
    System.out.println("=============="+new Date().getTime());
    List<Map> result = new ArrayList<Map>();
    for(Map item : maplist){  
        if(item!=null && "0".equals(item.get("pid").toString())){//ParentId = 0 表示顶级【文档项】  
            Map<String, Object> map = new HashMap<String, Object>();  
            map.put("id", item.get("id"));  
            map.put("pid", item.get("pid"));  
            map.put("name",item.get("name"));  
            getSonTree(map, maplist);  
            result.add(map);  
        }  
    }  
    String jsonString = JSON.toJSONString(result); 
    System.out.println("=============="+new Date().getTime());
    System.out.println("调用后台:"+jsonString);  
}
  
public static Map<String,Object> getSonTree(Map<String,Object> map,List<Map> itemList){  
    List<Map<String,Object>> sonList = new ArrayList<Map<String,Object>>();  
    Map<String, Object> sonMap;  
    for(Map item : itemList){  
        if(map.get("id").toString().equals(item.get("pid").toString())){  
            sonMap = new HashMap<String, Object>();  
            sonMap.put("id", item.get("id"));  
            sonMap.put("pid", item.get("pid"));  
            sonMap.put("name", item.get("name"));  
            sonList.add(sonMap);  
            getSonTree(sonMap,itemList);  
        }  
    }  
    map.put("children", sonList); 
    if(sonList.size()>0){
    	 map.put("isParent", true); 
    }
    return map;  
}
}
