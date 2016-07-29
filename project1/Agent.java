package ravensproject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;



// Uncomment these lines to access image processing.
//import java.awt.Image;
//import java.io.File;
//import javax.imageio.ImageIO;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(RavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent {
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
    public Agent() {
        
    }
    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a list representing its
     * confidence on each of the answers to the question: for example 
     * {.1,.1,.1,.1,.5,.1} for 6 answer problems or {.3,.2,.1,.1,0,0,.2,.1} for 8 answer problems.
     * 
     * In addition to returning your answer at the end of the method, your Agent
     * may also call problem.checkAnswer(double[] givenAnswer). The parameter
     * passed to checkAnswer should be your Agent's current guess for the
     * problem; checkAnswer will return the correct answer to the problem. This
     * allows your Agent to check its answer. Note, however, that after your
     * agent has called checkAnswer, it will *not* be able to change its answer.
     * checkAnswer is used to allow your Agent to learn from its incorrect
     * answers; however, your Agent cannot change the answer to a question it
     * has already answered.
     * 
     * If your Agent calls checkAnswer during execution of Solve, the answer it
     * returns will be ignored; otherwise, the answer returned at the end of
     * Solve will be taken as your Agent's answer to this problem.
     * 
     * @param problem the RavensProblem your agent should solve
     * @return your Agent's answer to this problem
     * @throws IOException 
     */
    public double[] Solve(RavensProblem problem) throws IOException {
    	HashMap<String,RavensFigure> figures=problem.getFigures();
    	RavensFigure figA=problem.getFigures().get("A");
    	RavensFigure figB=problem.getFigures().get("B");
    	RavensFigure figC=problem.getFigures().get("C"); 
    	RavensFigure fig1=problem.getFigures().get("1"); 
    	RavensFigure fig2=problem.getFigures().get("2"); 
    	RavensFigure fig3=problem.getFigures().get("3");
    	RavensFigure fig4=problem.getFigures().get("4"); 
    	RavensFigure fig5=problem.getFigures().get("5"); 
    	RavensFigure fig6=problem.getFigures().get("6");
    	ArrayList<RavensFigure> options=new ArrayList(6);
    	options.add(fig1);
    	options.add(fig2);
    	options.add(fig3);
    	options.add(fig4);
    	options.add(fig5);
    	options.add(fig6);  	
    	int	index=getAnswer("A","B","C",figures,6);
    	if(reflection("A","C",figures)){
    		for(int i=1;i<=6;i++){
    			if(reflection("B",Integer.toString(i),figures)){
    				index=i;
    			}
    		}
    	}else if(reflection("A","B",figures)){
    		for(int i=1;i<=6;i++){
    			if(reflection("C",Integer.toString(i),figures)){
    				index=i;
    			}
    	else{	
    		index=getAnswer("A","B","C",figures,6);
    	}
    		}
    	}
    	
    	if(fill("A","B",figures)){
    		for(int i=1;i<=6;i++){
    			if(fill("C",Integer.toString(i),figures)){
    				index=i;
    			}
    		}
    	}
    	if(fill("A","C",figures)){
    		for(int i=1;i<=6;i++){
    			if(fill("B",Integer.toString(i),figures)){
    				index=i;
    			}
    		}
    	}
    	
    	if(identicalFigures(figA,figB)){
    		for(int i=0;i<options.size();i++){    			
    			if(identicalFigures(figC,options.get(i))){
    				index=i+1;
    			}
    		}
    	}  
    	if(identicalFigures(figA,figC)){
    		for(int i=0;i<options.size();i++){    			
    			if(identicalFigures(figB,options.get(i))){
    				index=i+1;
    			}
    		}
    	}    	
        double[] answer = {.0,.0,.0,.0,.0,.0};                      
        answer[index-1]=1.0;
        
        System.out.println(Arrays.toString(answer));
        return answer;
    }
   
    private boolean fill(String fig1,String fig2,HashMap<String,RavensFigure> figures){
    	boolean result=false;   	
    	RavensFigure figA=figures.get(fig1);
        RavensFigure figB=figures.get(fig2);
    	HashMap<String,RavensObject> obA=figA.getObjects();
    	HashMap<String,RavensObject> obB=figB.getObjects();
    	Set<String> obAkey=obA.keySet();
    	Set<String> obBkey=obB.keySet();
    	for(String ka:obAkey){
    		RavensObject ro1=obA.get(ka);
    		HashMap<String,String> attr=ro1.getAttributes();
    		Set<String> attrKey=attr.keySet();
    		if(attrKey.contains("fill")){
    			if(attr.get("fill").compareTo("no")==0){
        			for(String kb:obBkey){
            			RavensObject ro2=obB.get(kb);
            			HashMap<String,String> attr2=ro2.getAttributes();
            			Set<String> attrKey2=attr2.keySet();
            			if(attrKey2.contains("fill")){
            				if(attr2.get("fill").compareTo("yes")==0){
            					if(attributeIdentical(attr,attr2,"fill")){
            						result=true;
            					}
                			}
            			}	
            		}
    			}else if(attr.get("fill").compareTo("yes")==0){
        			for(String kb:obBkey){
            			RavensObject ro2=obB.get(kb);
            			HashMap<String,String> attr2=ro2.getAttributes();
            			Set<String> attrKey2=attr2.keySet();
            			if(attrKey2.contains("fill")){
            				if(attr2.get("fill").compareTo("no")==0){
            					if(attributeIdentical(attr,attr2,"fill")){
            						result=true;
            					}
                			}
            			}	
            		}
    			}
    		}
    	}
    	
    	return result;
    }
    
    private boolean attributeIdentical(HashMap<String,String> attr1,HashMap<String,String> attr2,String attr){
    	boolean result=false;
    	Iterator<String> iter1=attr1.keySet().iterator();
    	while(iter1.hasNext()){
    		String attr1Key=(String)iter1.next();   		
    		if(!(attr1Key.equals(attr))){
    			if(attr1.get(attr1Key).equals(attr2.get(attr1Key))){
        			result=true;
        		}else{
        			result=false;
        			break;
        		}
    		}else {
    			continue;
    		}
    	}
    	return result;
    }
    
    private boolean reflection(String fig1,String fig2,HashMap<String,RavensFigure> figures){
    	boolean result=false;
    	if(shape(fig1,fig2,figures)){
    		RavensFigure figA=figures.get(fig1);
        	RavensFigure figB=figures.get(fig2);
    		HashMap<String,RavensObject> obA=figA.getObjects();
    		HashMap<String,RavensObject> obB=figB.getObjects();
    		Set<String> obAkey=obA.keySet();
    		Set<String> obBkey=obB.keySet();
    		for(String ka:obAkey){
    			RavensObject ro1=obA.get(ka);
    			HashMap<String,String> attr=ro1.getAttributes();
    			Set<String> attrKey=attr.keySet();
    			System.out.println(attr.get("angle"));
    			if(attrKey.contains("alignment")){
    				if(attr.get("alignment").compareTo("bottom-right")==0){
        				for(String kb:obBkey){
            				RavensObject ro2=obB.get(kb);
            				HashMap<String,String> attr2=ro2.getAttributes();
            				Set<String> attrKey2=attr2.keySet();
            				if(attrKey2.contains("alignment")){
            					if(ro2.getAttributes().get("alignment").compareTo("top-right")==0){
                					result=true;
                				}
            				}
            				
            			}
        			}else if(attr.get("alignment").compareTo("top-right")==0){
        				for(String kb:obBkey){
            				RavensObject ro2=obB.get(kb);
            				HashMap<String,String> attr2=ro2.getAttributes();
            				Set<String> attrKey2=attr2.keySet();
            				if(attrKey2.contains("alignment")){
            					if(ro2.getAttributes().get("alignment").compareTo("bottom-right")==0){
                					result=true;
                				}
            				}
      
            			}
        			}else if(attr.get("alignment").compareTo("bottom-left")==0){
        				for(String kb:obBkey){
            				RavensObject ro2=obB.get(kb);
            				HashMap<String,String> attr2=ro2.getAttributes();
            				Set<String> attrKey2=attr2.keySet();
            				if(attrKey2.contains("alignment")){
            					if(ro2.getAttributes().get("alignment").compareTo("top-left")==0){
                					result=true;
                				}
            				}
        
            			}
        			}else if(attr.get("alignment").compareTo("top-left")==0){
        				for(String kb:obBkey){
            				RavensObject ro2=obB.get(kb);
            				HashMap<String,String> attr2=ro2.getAttributes();
            				Set<String> attrKey2=attr2.keySet();
            				if(attrKey2.contains("alignment")){
            					if(ro2.getAttributes().get("alignment").compareTo("bottem-left")==0){
                					result=true;
                				}
            				}
            			}
        			}
    			}
    			if(attrKey.contains("angle")){
    				if(attr.get("angle").compareTo("0")==0){
        				for(String kb:obBkey){
            				RavensObject ro2=obB.get(kb);
            				HashMap<String,String> attr2=ro2.getAttributes();
                			Set<String> attrKey2=attr2.keySet();
                			if(attrKey2.contains("angle")){
                				if(ro2.getAttributes().get("angle").compareTo("90")==0){
                					result=true;
                				}
                			}
            			}
        			}else if(attr.get("angle").compareTo("90")==0){
        				for(String kb:obBkey){
            				RavensObject ro2=obB.get(kb);
            				HashMap<String,String> attr2=ro2.getAttributes();
                			Set<String> attrKey2=attr2.keySet();
                			if(attrKey2.contains("angle")){
                				if(ro2.getAttributes().get("angle").compareTo("180")==0){
                					result=true;
                				}
                			}
            			}
        			}else if(attr.get("angle").compareTo("180")==0){
        				for(String kb:obBkey){
            				RavensObject ro2=obB.get(kb);
            				HashMap<String,String> attr2=ro2.getAttributes();
                			Set<String> attrKey2=attr2.keySet();
                			if(attrKey2.contains("angle")){
                				if(ro2.getAttributes().get("angle").compareTo("270")==0){
                					result=true;
                				}
                			}
            			}
        			}else if(attr.get("angle").compareTo("270")==0){
        				for(String kb:obBkey){
            				RavensObject ro2=obB.get(kb);
            				HashMap<String,String> attr2=ro2.getAttributes();
                			Set<String> attrKey2=attr2.keySet();
                			if(attrKey2.contains("angle")){
                				if(ro2.getAttributes().get("angle").compareTo("0")==0){
                					result=true;
                				}
                			}
            			}
        			}
    			}
    		}
    	}
    	
    	return result;
    }
    private boolean identicalFigures(RavensFigure rf1,RavensFigure rf2){
    	boolean flag=false;
    	HashMap<String, RavensObject> ob1=rf1.getObjects();
    	HashMap<String, RavensObject> ob2=rf2.getObjects();
    	Set<String> key1=ob1.keySet();
    	Set<String> key2=ob2.keySet();
    	if(ob1.size()==ob2.size()){
    		for(String k:key1){
    			RavensObject ro1=ob1.get(k);
    			for(String t:key2){
    				RavensObject ro2=ob2.get(t);
    				if(objectsIdentical(ro1, ro2)){
    					flag=true;
    					continue;
    				}else{
    					flag=false;
    					break;
    				}
    			}
    		}
    	}
    	return flag;
    }   
    
    private boolean objectsIdentical(RavensObject ro1,RavensObject ro2){  	
    	HashMap<String,String> attr1=ro1.getAttributes();
    	HashMap<String,String> attr2=ro2.getAttributes();
    	if(attr1.size()!=attr2.size()){
    		return false;
    	}
    	boolean result=false;
    	String temp1;
    	String temp2;
    	for(Map.Entry<String,String>  entry:attr1.entrySet()){
    		if(attr2.containsKey(entry.getKey())){
    			temp1=entry.getValue();
    			temp2=attr2.get(entry.getKey());
    			//System.out.println(temp1);
    			//System.out.println(temp2);
    			if(null!=temp1&&null!=temp2){
    				if(temp1.equals(temp2)){
    					result=true;
    					continue;
    				}else{
    					result=false;
    					break;
    				}
    			}else if(null==temp1&&null==temp2){
    				result=true;
    				continue;
    			}else{
    				result=false;
    				break;
    			}
    		}else{
    			result=false;
    			break;
    		}
    	}
    	return result;

    }
    private boolean shape(String fig1,String fig2,HashMap<String,RavensFigure> figures){
    	int pixel1=imagePixel(figures.get(fig1));
    	int pixel2=imagePixel(figures.get(fig2));
    	int refPixel=pixel1-pixel2;
    	//System.out.println(pixel1);
    	//System.out.println(pixel2);
    	boolean result=false;
    	if(Math.abs(refPixel)<200){
    		result=true;
    	}
    	return result;
    }
    
    private int shape(String fig1,String fig2,String fig3,HashMap<String,RavensFigure> figures,int type){
    	int pixel3=imagePixel(figures.get(fig3));
    	int candidateAnswer=0;
    	boolean result=false;
    	if(shape(fig1,fig2,figures)){
    		for(int i=1;i<=type;i++){
        		double differencePixel=pixel3-imagePixel(figures.get(Integer.toString(i)));
        		if(differencePixel==0){      			
        			candidateAnswer=i;;
        		}
        	}
    	}  	   	
    	return candidateAnswer;
    }
    
    private int getAnswer(String fig1,String fig2,String fig3,HashMap<String,RavensFigure> figures,int type) throws IOException{
    	int pixel1=imagePixel(figures.get(fig1));
    	int pixel2=imagePixel(figures.get(fig2));
    	int pixel3=imagePixel(figures.get(fig3));
    	int refPixel=pixel1-pixel2;
    	int closestPixel=Integer.MAX_VALUE;
    	int candidateAnswer=-1;
    	for(int i=1;i<=type;i++){
    		int differencePixel=pixel3-imagePixel(figures.get(Integer.toString(i)));
    		if(Math.abs(differencePixel-refPixel)<closestPixel){
    			closestPixel=Math.abs(differencePixel-refPixel);
    			candidateAnswer=i;
    		}
    	}
    	return candidateAnswer;
    }
    private int imagePixel(RavensFigure f){   	
		BufferedImage figureImage=null;
		int pixel=0;
		try {
			figureImage = ImageIO.read(new File(f.getVisual()));
			int color=0;
			for(int i=0;i<figureImage.getWidth();i++){
		    	for(int j=0;j<figureImage.getHeight();j++){
		    		color+=figureImage.getRGB(i, j)!=-1?1:0;
		    	}
			}
			pixel=color;
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return pixel;
    }
}
