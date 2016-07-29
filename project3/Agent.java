package ravensproject;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
    	int index=1;
    	double[] answer = {.0,.0,.0,.0,.0,.0,.0,.0};    
    	if (problem.getProblemType().equals("2x2")){
    		index=getAnswer("A","B","C",figures,6); 
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
        	
        	if(identicalFigures(figures.get("A"),figures.get("B"))){
        		for(int i=1;i<=6;i++){    			
        			if(identicalFigures(figures.get("C"),figures.get(String.valueOf(i)))){
        				index=i;
        			}
        		}
        	}  
        	if(identicalFigures(figures.get("A"),figures.get("C"))){
        		for(int i=1;i<=6;i++){    			
        			if(identicalFigures(figures.get("B"),figures.get(String.valueOf(i)))){
        				index=i;
        			}
        		}
        	}    	
    		answer[index-1]=1.0;
    		System.out.println(Arrays.toString(answer));
    		return answer;
    	}else if(problem.getProblemType().equals("3x3")&&problem.getName().contains("Problem C")){
    		index=getAnswer("G","H","H",figures,8); 
    		
    		RavensFigure figA=figures.get("A");
    		BufferedImage imageA=transfer(figA);
    		int countA=countShapes(imageA);
    		RavensFigure figB=figures.get("B");
    		BufferedImage imageB=transfer(figB);
    		int countB=countShapes(imageB);
    		RavensFigure figC=figures.get("C");
    		BufferedImage imageC=transfer(figC);
    		int countC=countShapes(imageC);
    		RavensFigure figD=figures.get("D");
    		BufferedImage imageD=transfer(figD);
    		int countD=countShapes(imageD);
    		RavensFigure figE=figures.get("E");
    		BufferedImage imageE=transfer(figE);
    		int countE=countShapes(imageE);
    		RavensFigure figF=figures.get("F");
    		BufferedImage imageF=transfer(figF);
    		int countF=countShapes(imageF);
    		RavensFigure figG=figures.get("G");
    		BufferedImage imageG=transfer(figG);
    		int countG=countShapes(imageG);
    		RavensFigure figH=figures.get("H");
    		BufferedImage imageH=transfer(figH);
    		int countH=countShapes(imageH);   		
    		int count=countB-countA;   		   		
    		if(vTransition(percentSimilarFigImage(transition(figures.get("A")),figures.get("C")))){
    			if(vTransition(percentSimilarFigImage(transition(figures.get("D")),figures.get("F")))){
    				for (int i = 1; i <= 8; i++) {
						if (vTransition(percentSimilarFigImage(transition(figures.get("G")),figures.get(String.valueOf(i))))) {
							index= i;
						}
					}
    			}
    		} 		
    		if(countC==countB+count&&count!=0){
    			if(countE==countD+count&&countF==countE+count){
        			for(int i=1;i<=8;i++){
        				if(countShapes(transfer(figures.get(String.valueOf(i))))==countH+count){
            				index=i;
            			}
        			}
        		}
    		}
    		
    		if(increaseOne(figures.get("A"),figures.get("B"),figures.get("C"))){
    			if(increaseOne(figures.get("D"),figures.get("E"),figures.get("F"))){
    				double ans=0.0;
    				for (int i = 1; i <= 8; i++) {
    					if (increaseOne(figures.get("G"),figures.get("H"),figures.get(String.valueOf(i)))&&percentSimilarTwoFigures(figures.get("H"),figures.get(String.valueOf(i)))>ans) {
							ans=percentSimilarTwoFigures(figures.get("H"),figures.get(String.valueOf(i)));	
							index=i;
						}
					}
    			}
    		}
    		if(vReflection(percentSimilarTwoRefl(figures.get("A"),figures.get("C")))){
    			if(vReflection(percentSimilarTwoRefl(figures.get("D"),figures.get("F")))){
    				for (int i = 1; i <= 8; i++) {
						if (vReflection(percentSimilarTwoRefl(figures.get("G"),figures.get(String.valueOf(i))))) {
							index= i;
						}
					}
    			}
    		}  
    		List<RavensFigure> column1=new ArrayList();
        	List<RavensFigure> column2=new ArrayList();
        	List<RavensFigure> column3=new ArrayList();
        	column1.add(figures.get("A"));
        	column1.add(figures.get("D"));
        	column1.add(figures.get("G"));
        	column2.add(figures.get("B"));
        	column2.add(figures.get("E"));
        	column2.add(figures.get("H"));
        	column3.add(figures.get("C"));
        	column3.add(figures.get("F"));
        	double column1_pixel=imagePixel(column1,true);
        	double column2_pixel=imagePixel(column2,true);
        	double column3_pixel=imagePixel(column3,true);
        	double option_column=Math.abs(2*column2_pixel-column1_pixel-column3_pixel);        	
        	if((Math.abs(column2_pixel-column1_pixel)/column2_pixel)<0.015){
        		for(int i=1;i<=8;i++){
            		double temp_pix = imagePixel(figures.get(String.valueOf(i)));
            		if (Math.abs(temp_pix - option_column) <= 0.05*option_column) {
        				index=i;
        				break;
        			}
            	}
        	}        	
        	double error=0.015*option_column;
        	for(int i=1;i<=8;i++){
        		double temp_pix = imagePixel(figures.get(String.valueOf(i)));
        		if (Math.abs(temp_pix - option_column) <= error) {
    				index=i;
    				break;
    			}
        	}        	
    		answer[index-1]=1.0;
    		System.out.println(Arrays.toString(answer));
    		return answer;
    	}
    	else if(problem.getName().contains("Problem D")&&problem.getProblemType().equals("3x3")){
    		
    		RavensFigure figA=figures.get("A");
    		BufferedImage imageA=transfer(figA);
    		int countA=countShapes(imageA);
    		RavensFigure figB=figures.get("B");
    		BufferedImage imageB=transfer(figB);
    		int countB=countShapes(imageB);
    		RavensFigure figC=figures.get("C");
    		BufferedImage imageC=transfer(figC);
    		int countC=countShapes(imageC);
    		int countRow1=countA+countB+countC;
    		//System.out.println(countRow1);
    		RavensFigure figD=figures.get("D");
    		BufferedImage imageD=transfer(figD);
    		int countD=countShapes(imageD);
    		RavensFigure figE=figures.get("E");
    		BufferedImage imageE=transfer(figE);
    		int countE=countShapes(imageE);
    		RavensFigure figF=figures.get("F");
    		BufferedImage imageF=transfer(figF);
    		int countF=countShapes(imageF);
    		int countRow2=countD+countE+countF;
    		//System.out.println(countRow2);
    		RavensFigure figG=figures.get("G");
    		BufferedImage imageG=transfer(figG);
    		int countG=countShapes(imageG);
    		RavensFigure figH=figures.get("H");
    		BufferedImage imageH=transfer(figH);
    		int countH=countShapes(imageH);
    		int countRow3=countG+countH;
    		//System.out.println(countRow3);
    		int totalColumnShapes=countA+countD+countG;
    		
    		if(countA!=countD&&countA!=countG&&countD!=countG){
    			if(totalColumnShapes==countB+countE+countH){
        			int ans=totalColumnShapes-countC-countF;
        			for(int i=1;i<=8;i++){
        				if(countShapes(transfer(figures.get(String.valueOf(i))))==ans){
            				index=i;
            			}
        			}
        		}
    		}
    		
        	List<RavensFigure> column1=new ArrayList();
        	List<RavensFigure> column2=new ArrayList();
        	List<RavensFigure> column3=new ArrayList();
        	column1.add(figures.get("A"));
        	column1.add(figures.get("D"));
        	column1.add(figures.get("G"));
        	double column1_pixel=imagePixel(column1,true); 
        	column2.add(figures.get("B"));
        	column2.add(figures.get("E"));
        	column2.add(figures.get("H"));
        	double column2_pixel=imagePixel(column2,true);
        	column3.add(figures.get("C"));
        	column3.add(figures.get("F"));
        	double column3_pixel=imagePixel(column3,true);
        	double option_pixel=Math.abs(2*column2_pixel-column1_pixel-column3_pixel);
        	for(int i=1;i<=8;i++){
    			double temp_pix = imagePixel(figures.get(String.valueOf(i))); 
    			if (Math.abs(temp_pix - option_pixel) <= 0.015*option_pixel) {
					index=i;
					break;
				}
    		} 
        	
    		
        	if(twoShapesToOne(figures)){
    			double percent=twoSubtractionToOne(figures.get("C"),figures.get("E"),figures.get("G"));
    			option_pixel=Math.abs(imagePixel(figures.get("D"))-percent*imagePixel(figures.get("B")));
    			for(int i=1;i<=8;i++){
        			double temp_pix = imagePixel(figures.get(String.valueOf(i)));
        			if (Math.abs(temp_pix - option_pixel) <= 0.015*option_pixel) {
    					index=i;
    					break;
    				}
        		}	
    		}
    		
    		
        	if(twoShapesUnionToOne2(figures)){   			
    			for(int i=1;i<=8;i++){
    				double union=combinationPixel(figures.get("B"),
        					figures.get("D"), 1);
        			if (Math.abs(union-imagePixel(figures.get(String.valueOf(i)))) <= 0.05*union) {
    					index=i;
    					break;
    				}
        		}	
    		}
        	
    		
        	if(twoShapesUnionToOne(figures)){   			
    			double single=imagePixel(figures.get("D"));		
    			for(int i=1;i<=8;i++){
    				double union=combinationPixel(figures.get("B"),
        					figures.get(String.valueOf(i)), 1);
        			if (Math.abs(union-single) <= 0.05*union) {
    					index=i;
    					break;
    				}
        		}	
    		}
        	
        	
    		
    		if(Math.abs(percentSimilarTwoFigures(figures.get("A"),figures.get("B"))-percentSimilarTwoFigures(figures.get("D"),figures.get("E")))<0.015){
    			if(Math.abs(percentSimilarTwoFigures(figures.get("B"),figures.get("C"))-percentSimilarTwoFigures(figures.get("E"),figures.get("F")))<0.015){
    				for(int i=1;i<=8;i++){    	
    					if(Math.abs(percentSimilarTwoFigures(figures.get("B"),figures.get("C"))-percentSimilarTwoFigures(figures.get("H"),figures.get(Integer.toString(i))))<0.015){
    	    				index=i;	
    	    				break;
    					}
    				}
    			}
    		}
  			
    		
    		if(OrAB(figures.get("E"),figures.get("B"),figures.get("D"))){
    			if(percentSimilarTwoFigures(figures.get("G"),figures.get("H"))>0.985){
    				if(percentSimilarTwoFigures(figures.get("C"),figures.get("F"))>0.985){
    					BufferedImage image=combinationImage(figures.get("F"),figures.get("H"),1);
    					double ans=0.0;
    					for (int i = 1; i <= 8; i++) {
    						if (percentSimilarFigImage(image,figures.get(String.valueOf(i)))>ans) {
    							ans=percentSimilarFigImage(image,figures.get(String.valueOf(i)));
    							index= i;
    						
    						}
    					}
    				}			
					
    			}
    		}   	
			List<Integer> list=new ArrayList();
    		if(onlyOne(figures)){
				if(countRow1==countRow2){
					if(countA!=countB||countB!=countC){
						int ans=countRow1-countRow3;
						for(int i=1;i<=8;i++){
							if(countShapes(transfer(figures.get(String.valueOf(i))))==ans){
								list.add(i);
							}
						}
					}
				}
			}
    		for(int l:list){
				if(!IsOption(figures,figures.get(String.valueOf(l)))){
					if(!IsOptionPixel(figures,figures.get(String.valueOf(l)))){
						index=l;
					}	
				}
			}
    		answer[index-1]=1.0;
    		System.out.println(Arrays.toString(answer));
    		return answer;
        	
    	}else if(problem.getProblemType().equals("3x3")&&problem.getName().contains("Problem E")){   		
    		
    		if(topBottomAB(figures.get("C"),figures.get("A"),figures.get("B"))){
				if(topBottomAB(figures.get("F"),figures.get("D"),figures.get("E"))){
					BufferedImage image=combinationImage(figures.get("G"),figures.get("H"),3);
					double ans=0.0;
					for (int i = 1; i <= 8; i++) {
						if (percentSimilarFigImage(image,figures.get(String.valueOf(i)))>ans) {
							ans=percentSimilarFigImage(image,figures.get(String.valueOf(i)));
							index= i;
						}
					}
				}
			}  		
    		if((combinationPixel(figures.get("A"),figures.get("B"),2)-imagePixel(figures.get("C")))/combinationPixel(figures.get("A"),figures.get("B"),2)>0.9){
    			if((combinationPixel(figures.get("D"),figures.get("E"),2)-imagePixel(figures.get("F")))/combinationPixel(figures.get("D"),figures.get("E"),2)>0.9){
    				for(int i=1;i<=8;i++){
    					if((combinationPixel(figures.get("G"),figures.get("H"),2)-imagePixel(figures.get(String.valueOf(i))))/combinationPixel(figures.get("G"),figures.get("H"),2)>0.9){
    						index=i;
    					}
    				}
    			}
    		}    		
    		if(XorAB(figures.get("G"),figures.get("A"),figures.get("D"))){
				if(XorAB(figures.get("H"),figures.get("B"),figures.get("E"))){
					for (int i = 1; i <= 8; i++) {
						if (XorAB(figures.get(String.valueOf(i)),figures.get("C"),figures.get("F"))) {
							index= i;
						}
					}
				}
			}  		
    		if(XorAB(figures.get("C"),figures.get("A"),figures.get("B"))){
				if(XorAB(figures.get("F"),figures.get("D"),figures.get("E"))){
					BufferedImage image=combinationImage(figures.get("G"),figures.get("H"),2);
					double ans=0.0;
					for (int i = 1; i <= 8; i++) {
						if (percentSimilarFigImage(image,figures.get(String.valueOf(i)))>ans) {
							ans=percentSimilarFigImage(image,figures.get(String.valueOf(i)));
							index= i;
						}
					}
				}
			}			
    		List<Integer> listOr=new ArrayList();
    		if(OrAB(figures.get("D"),figures.get("A"),figures.get("G"))){
				if(OrAB(figures.get("E"),figures.get("B"),figures.get("H"))){
					double ans=0.0;
					for (int i = 1; i <= 8; i++) {
						BufferedImage image=combinationImage(figures.get("C"),figures.get(String.valueOf(i)),1);
						if (percentSimilarFigImage(image,figures.get("F"))>=ans) {
							ans=percentSimilarFigImage(image,figures.get("F"));
							listOr.add(i);;
						}
					}
				}
			}
    		for(int l:listOr){
				if(!IsOption(figures,figures.get(String.valueOf(l)))){
					index=l;
				}
			} 		
    		if(OrAB(figures.get("C"),figures.get("A"),figures.get("B"))){
				if(OrAB(figures.get("F"),figures.get("D"),figures.get("E"))){
					BufferedImage image=combinationImage(figures.get("G"),figures.get("H"),1);
					double ans=0.0;
					for (int i = 1; i <= 8; i++) {
						if (percentSimilarFigImage(image,figures.get(String.valueOf(i)))>=ans) {
							ans=percentSimilarFigImage(image,figures.get(String.valueOf(i)));
							index= i;
							
						}
					}
				}
			}		
			if(AndAB(figures.get("C"),figures.get("A"),figures.get("B"))){
				if(AndAB(figures.get("F"),figures.get("D"),figures.get("E"))){
					for (int i = 1; i <= 8; i++) {
						if (AndAB(figures.get(String.valueOf(i)),figures.get("G"),figures.get("H"))) {
							index= i;
						}
					}
				}
			}	
			
    		
			List<Integer> list=new ArrayList();
			if(twoShapesSumToOne(figures.get("A"),figures.get("B"),figures.get("C"))){
				if(twoShapesSumToOne(figures.get("D"),figures.get("E"),figures.get("F"))){
					for (int i = 1; i <= 8; i++) {
						if (twoShapesSumToOne(figures.get("G"),figures.get("H"),figures.get(String.valueOf(i)))) {
							list.add(i);
						}
					}
				}
			}
			
			for(int l:list){
				if(!IsOption(figures,figures.get(String.valueOf(l)))){
					index=l;
				}
			}
			
			answer[index-1]=1.0;
			System.out.println(Arrays.toString(answer));
	    	return answer; 
    	}
        return answer;
    }
    private boolean onlyOne(HashMap<String,RavensFigure> figures){
    	boolean flag=false;
    	int sameFig=0;
    	for(int i=1;i<=8;i++){
    		if(IsOption2(figures,figures.get(String.valueOf(i)))){
    			sameFig++;
    		}
    	}
    	if(sameFig>=7) return true;
    	return flag;
    }
    private int countShapes(BufferedImage image){
    	int numShapes=0;
    	int blackColor = new Color(0,0,0).getRGB();
    	for(int i=0;i<image.getWidth();i++){
    		for(int j=0;j<image.getHeight();j++){
    			if(image.getRGB(i,j)==blackColor){
    				numShapes=traceShape(i,j,numShapes,image);    				
    			}
    		}
    	}
    	return numShapes;
    }
    public int traceShape(int i,int j,int numShape,BufferedImage image){
    	++numShape;
    	//System.out.println(numShape);
    	resetColor(new Point(i,j),image);
    	return numShape;
    }
    public void resetColor(Point startPoint,BufferedImage image){
    	int reColor = new Color(50,50,50).getRGB();
    	List<Point> list=new ArrayList();
    	list.add(startPoint);
    	while(!list.isEmpty()){
    		Point curPoint=list.get(0);
    		image.setRGB((int)curPoint.getX(),(int)curPoint.getY(),reColor);
    		Point newPoint=null;
    		newPoint=new Point((int)curPoint.getX()-1,(int)curPoint.getY());
    		if(isBlack(newPoint,image)){
    			if(!list.contains(newPoint)){
    				list.add(newPoint);
    			}
    		}
    		newPoint=new Point((int)curPoint.getX(),(int)curPoint.getY()-1);
    		if(isBlack(newPoint,image)){
    			if(!list.contains(newPoint)){
    				list.add(newPoint);
    			}
    		}
    		newPoint=new Point((int)curPoint.getX()+1,(int)curPoint.getY());
    		if(isBlack(newPoint,image)){
    			if(!list.contains(newPoint)){
    				list.add(newPoint);
    			}
    		}
    		newPoint=new Point((int)curPoint.getX(),(int)curPoint.getY()+1);
    		if(isBlack(newPoint,image)){
    			if(!list.contains(newPoint)){
    				list.add(newPoint);
    			}
    		}
    		newPoint=new Point((int)curPoint.getX()-1,(int)curPoint.getY()-1);
    		if(isBlack(newPoint,image)){
    			if(!list.contains(newPoint)){
    				list.add(newPoint);
    			}
    		}
    		newPoint=new Point((int)curPoint.getX()+1,(int)curPoint.getY()+1);
    		if(isBlack(newPoint,image)){
    			if(!list.contains(newPoint)){
    				list.add(newPoint);
    			}
    		}
    		newPoint=new Point((int)curPoint.getX()-1,(int)curPoint.getY()+1);
    		if(isBlack(newPoint,image)){
    			if(!list.contains(newPoint)){
    				list.add(newPoint);
    			}
    		}
    		newPoint=new Point((int)curPoint.getX()+1,(int)curPoint.getY()-1);
    		if(isBlack(newPoint,image)){
    			if(!list.contains(newPoint)){
    				list.add(newPoint);
    			}
    		}
    		list.remove(0);
    	}
    }
    private boolean isBlack(Point point,BufferedImage image){
    	int blackColor = new Color(0,0,0).getRGB();
    	if(!isValidPoint(point,image)) return false;
    	if(image.getRGB((int)point.getX(), (int)point.getY())==blackColor){
    		return true;
    	}
    	return false;
    }
    private boolean isValidPoint(Point point,BufferedImage image){
    	if(point.getX()<0) return false;
    	if(point.getY()<0) return false;
    	if(point.getX()>=image.getWidth()) return false;
    	if(point.getY()>=image.getHeight()) return false;
    	return true;
    }
    private boolean AndAB(RavensFigure f1,RavensFigure f2,RavensFigure f3){
    	boolean flag=false;
    	BufferedImage image=combinationImage(f2,f3,0);
    	double percent=percentSimilarFigImage(image,f1);
    	if(percent>0.95){
    		flag=true;
    	}
    	return flag;
    }
    private boolean XorAB(RavensFigure f1,RavensFigure f2,RavensFigure f3){
    	boolean flag=false;
    	BufferedImage image=combinationImage(f2,f3,2);
    	double percent=percentSimilarFigImage(image,f1);
    	if(percent>0.95){
    		flag=true;
    	}
    	return flag;
    }
    private boolean OrAB(RavensFigure f1,RavensFigure f2,RavensFigure f3){
    	boolean flag=false;
    	BufferedImage image=combinationImage(f2,f3,1);
    	double percent=percentSimilarFigImage(image,f1);
    	if(percent>0.95){
    		flag=true;
    	}
    	return flag;
    }
    private boolean topBottomAB(RavensFigure f1,RavensFigure f2,RavensFigure f3){
    	boolean flag=false;
    	BufferedImage image=combinationImage(f2,f3,3);
    	double percent=percentSimilarFigImage(image,f1);
    	if(percent>0.95){
    		flag=true;
    	}
    	return flag;
    }
    private boolean IsOption(HashMap<String,RavensFigure> figures, RavensFigure ans) {
    	boolean flag=false;
    	RavensFigure figA=figures.get("A");
    	RavensFigure figB=figures.get("B");
    	RavensFigure figC=figures.get("C");
    	RavensFigure figD=figures.get("D");
    	RavensFigure figE=figures.get("E");
    	RavensFigure figF=figures.get("F");
    	RavensFigure figG=figures.get("G");
    	RavensFigure figH=figures.get("H");	
    	if((percentSimilarTwoFigures(figA,ans)>0.985)||(percentSimilarTwoFigures(figB,ans)>0.985)||(percentSimilarTwoFigures(figC,ans)>0.985)||(percentSimilarTwoFigures(figD,ans)>0.985)||(percentSimilarTwoFigures(figE,ans)>0.985)||(percentSimilarTwoFigures(figF,ans)>0.985)||(percentSimilarTwoFigures(figG,ans)>0.985)||(percentSimilarTwoFigures(figH,ans)>0.985)){
    		flag=true;
    	}
    	return flag;
	}
    private boolean IsOption2(HashMap<String,RavensFigure> figures, RavensFigure ans) {
    	boolean flag=false;
    	RavensFigure figA=figures.get("A");
    	RavensFigure figB=figures.get("B");
    	RavensFigure figC=figures.get("C");
    	RavensFigure figD=figures.get("D");
    	RavensFigure figE=figures.get("E");
    	RavensFigure figF=figures.get("F");
    	RavensFigure figG=figures.get("G");
    	RavensFigure figH=figures.get("H");	
    	if((percentSimilarTwoFigures(figA,ans)>0.95)||(percentSimilarTwoFigures(figB,ans)>0.95)||(percentSimilarTwoFigures(figC,ans)>0.95)||(percentSimilarTwoFigures(figD,ans)>0.95)||(percentSimilarTwoFigures(figE,ans)>0.95)||(percentSimilarTwoFigures(figF,ans)>0.95)||(percentSimilarTwoFigures(figG,ans)>0.95)||(percentSimilarTwoFigures(figH,ans)>0.95)){
    		flag=true;
    	}
    	return flag;
	}
   
    private boolean IsOptionPixel(HashMap<String,RavensFigure> figures, RavensFigure ans) {
    	boolean flag=false;
    	RavensFigure figA=figures.get("A");
    	RavensFigure figB=figures.get("B");
    	RavensFigure figC=figures.get("C");
    	RavensFigure figD=figures.get("D");
    	RavensFigure figE=figures.get("E");
    	RavensFigure figF=figures.get("F");
    	RavensFigure figG=figures.get("G");
    	RavensFigure figH=figures.get("H");	
    	if((Math.abs(imagePixel(figA)-imagePixel(ans))/imagePixel(figA)<0.015)||(Math.abs(imagePixel(figB)-imagePixel(ans))/imagePixel(figB)<0.015)||(Math.abs(imagePixel(figC)-imagePixel(ans))/imagePixel(figC)<0.015)||(Math.abs(imagePixel(figD)-imagePixel(ans))/imagePixel(figD)<0.015)||(Math.abs(imagePixel(figE)-imagePixel(ans))/imagePixel(figE)<0.015)||(Math.abs(imagePixel(figF)-imagePixel(ans))/imagePixel(figF)<0.015)||(Math.abs(imagePixel(figG)-imagePixel(ans))/imagePixel(figG)<0.015)||(Math.abs(imagePixel(figH)-imagePixel(ans))/imagePixel(figH)<0.015)){
    		flag=true;
    	}
    	return flag;
	}
    private double percentSimilarFigImage(BufferedImage image,RavensFigure f){
    	BufferedImage figureImage=null;
    	try {
			figureImage = ImageIO.read(new File(f.getVisual()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	double percent=0.0;
    	int sameColor=0;
    	int diffColor=0;
    	for(int i=0;i<figureImage.getWidth();i++){
    		for(int j=0;j<figureImage.getHeight();j++){
    			if(figureImage.getRGB(i, j)==image.getRGB(i, j)){
        			sameColor++;
        		}else{
        			diffColor++;
        		}
    		}    			  			
    		
    	}
    	percent=(double)sameColor/(diffColor+sameColor);
    	return percent;
    }
    private double percentSimilarTwoFigures(RavensFigure f1,RavensFigure f2){
    	BufferedImage figureImage1=null;
    	BufferedImage figureImage2=null;
    	try {
			figureImage1 = ImageIO.read(new File(f1.getVisual()));
			figureImage2 = ImageIO.read(new File(f2.getVisual()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	double percent=0.0;
    	int sameColor=0;
    	int diffColor=0;
    	for(int i=0;i<figureImage1.getWidth();i++){
    		for(int j=0;j<figureImage1.getHeight();j++){
    			if(figureImage1.getRGB(i, j)==figureImage2.getRGB(i, j)){
        			sameColor++;
        		}else{
        			diffColor++;
        		}
    		}  			
    		
    	}
    	percent=(double)sameColor/(diffColor+sameColor);
    	return percent;
    }
    private double percentSimilarTwoRefl(RavensFigure f1,RavensFigure f2){
    	BufferedImage figureImage1=null;
    	BufferedImage figureImage2=null;
    	try {
			figureImage1 = ImageIO.read(new File(f1.getVisual()));
			figureImage2 = ImageIO.read(new File(f2.getVisual()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	double percent=0.0;
    	int sameColor=0;
    	int diffColor=0;
    	int k=figureImage1.getWidth()-1;
    	for(int i=0;i<figureImage1.getWidth();i++){
    		for(int j=0;j<figureImage1.getHeight();j++){
    			if(figureImage1.getRGB(i, j)==figureImage2.getRGB(k-i, j)){
    				sameColor++;
    			}else{
    				diffColor++;
    			}   			
    		}
    	}
    	percent=(double)sameColor/(diffColor+sameColor);
    	return percent;
    }
    private boolean increaseOne(RavensFigure f1,RavensFigure f2,RavensFigure f3){
    	boolean flag=false;
    	double p1=imagePixel(f1);
    	double p2=imagePixel(f2);
    	double p3=imagePixel(f3);
    	double percent=Math.abs((p2-p1) /(p3-p2));
    	if(percent==1.0) flag=true;
    	return flag;
    }    	
    private boolean vReflection(double percent){
    	boolean flag=false;
    	if(percent>0.95){
    		flag=true;
    	}
    	return flag;
    }
    private boolean vTransition(double percent){
    	boolean flag=false;
    	if(percent>0.9){
    		flag=true;
    	}
    	return flag;
    }  
    private BufferedImage transfer(RavensFigure f){
    	int blackColor = new Color(0,0,0).getRGB();
    	int whiteColor = new Color(255,255,255).getRGB();
    	BufferedImage figureImage=null;
    	BufferedImage image=null;
    	try {
			figureImage = ImageIO.read(new File(f.getVisual()));
			image=new BufferedImage(figureImage.getWidth(),figureImage.getHeight(),figureImage.getType());
			for(int i=0;i<figureImage.getWidth();i++){
				for(int j=0;j<figureImage.getHeight();j++){
					boolean setBlack0 = false;
					if(figureImage.getRGB(i, j)!=-1){
						image.setRGB(i, j, blackColor);
						setBlack0=true;
					}
					if(!setBlack0)
						  image.setRGB(i, j, whiteColor);
				}
			}
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	return image;
    }
    private BufferedImage transition(RavensFigure f){
    	int blackColor = new Color(0,0,0).getRGB();
    	int whiteColor = new Color(255,255,255).getRGB();
    	BufferedImage image=null;
    	BufferedImage figureImage=null;
    	try {
			figureImage = ImageIO.read(new File(f.getVisual()));
			image=new BufferedImage(figureImage.getWidth(),figureImage.getHeight(),figureImage.getType());
			int k=figureImage.getWidth()/2;
			for(int i=0;i<figureImage.getWidth()/2;i++){
				for(int j=0;j<figureImage.getHeight();j++){
					boolean setBlack0 = false;
					if(figureImage.getRGB(k+i, j)!=-1){
						image.setRGB(i, j, blackColor);
						setBlack0=true;
					}
					if(!setBlack0)
						  image.setRGB(i, j, whiteColor);
				}
			}
			for(int i=figureImage.getWidth()/2;i<figureImage.getWidth();i++){
				for(int j=0;j<figureImage.getHeight();j++){
					boolean setBlack0 = false;
					if(figureImage.getRGB(i-k, j)!=-1){
						image.setRGB(i, j, blackColor);
						setBlack0=true;
					}
					if(!setBlack0)
						  image.setRGB(i, j, whiteColor);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return image;
    }
    private double combinationPixel(RavensFigure f1, RavensFigure f2,
			int state){
    	BufferedImage Image=null;
    	BufferedImage figureImage1=null;
    	BufferedImage figureImage2=null;
    	double pixel=0;
    	try {
			figureImage1 = ImageIO.read(new File(f1.getVisual()));
			figureImage2 = ImageIO.read(new File(f2.getVisual()));
			for(int i=0;i<figureImage1.getWidth();i++){
				for(int j=0;j<figureImage1.getHeight();j++){
					switch(state){
					case 0:
						pixel+=figureImage1.getRGB(i, j)!=-1&&figureImage2.getRGB(i, j)!=-1?1:0;
						break;
					case 1:
						pixel+=figureImage1.getRGB(i, j)!=-1||figureImage2.getRGB(i, j)!=-1?1:0;
						break;
					case 2:
						if(figureImage1.getRGB(i, j) != -1
								|| figureImage2.getRGB(i, j) != -1){
							if(!(figureImage1.getRGB(i, j) != -1
									&& figureImage2.getRGB(i, j) != -1)){
								pixel++;
							}
						}
						break;
					case 3:
						if(j<figureImage1.getHeight()/2){
							if(figureImage1.getRGB(i, j)!=-1){
								pixel++;
							}
						}else{
							if(figureImage2.getRGB(i, j)!=-1){
								pixel++;
							}
						}
						break;
					}						
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return pixel;
    }
    private BufferedImage combinationImage(RavensFigure f1, RavensFigure f2,
			int state){
    	int blackColor = new Color(0,0,0).getRGB();
    	int whiteColor = new Color(255,255,255).getRGB();
    	BufferedImage figureImage1=null;
    	BufferedImage figureImage2=null;
    	BufferedImage image=null;
    	try {
			figureImage1 = ImageIO.read(new File(f1.getVisual()));
			figureImage2 = ImageIO.read(new File(f2.getVisual()));
			image=new BufferedImage(figureImage1.getWidth(),figureImage1.getHeight(),figureImage1.getType());
			for(int i=0;i<figureImage1.getWidth();i++){
				for(int j=0;j<figureImage1.getHeight();j++){
					switch(state){
					case 0:
						boolean setBlack0 = false;
						if(figureImage1.getRGB(i, j)!=-1&&figureImage2.getRGB(i, j)!=-1){
							image.setRGB(i, j, blackColor);
							setBlack0 = true;
						}
						if(!setBlack0)
							  image.setRGB(i, j, whiteColor);
						break;
					case 1:
						boolean setBlack1 = false;
						if(figureImage1.getRGB(i, j)!=-1||figureImage2.getRGB(i, j)!=-1){
							image.setRGB(i, j, blackColor);
							setBlack1 = true;
						}
						if(!setBlack1)
							  image.setRGB(i, j, whiteColor);
						break;
					case 2:
						boolean setBlack2 = false;
						if(figureImage1.getRGB(i, j) != -1
								|| figureImage2.getRGB(i, j) != -1){
							if(!(figureImage1.getRGB(i, j) != -1
									&& figureImage2.getRGB(i, j) != -1)){
								image.setRGB(i, j, blackColor);
								setBlack2=true;
							}
						}
						if(!setBlack2)
							  image.setRGB(i, j, whiteColor);
						break;
					case 3:
						boolean setBlack3 = false;
						if(j<figureImage1.getHeight()/2){
							if(figureImage1.getRGB(i, j)!=-1){
								image.setRGB(i, j, blackColor);
								setBlack3 = true;
							}
						}else{
							if(figureImage2.getRGB(i, j)!=-1){
								image.setRGB(i, j, blackColor);
								setBlack3 = true;
							}
						}
						if(!setBlack3)
							  image.setRGB(i, j, whiteColor);
						break;
					}						
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return image;
    }
    private boolean twoShapesSumToOne(RavensFigure f1,RavensFigure f2,RavensFigure f3){
    	boolean flag=false;
    	if(Math.abs((imagePixel(f2)+imagePixel(f3)-imagePixel(f1)))<0.015*imagePixel(f1)){
    		flag=true;
    	}
    	return flag;
    }
    private boolean twoShapesUnionToOne(HashMap<String,RavensFigure> figures){
    	boolean flag=false;
    	RavensFigure figC=figures.get("C");
    	RavensFigure figE=figures.get("E");
    	RavensFigure figG=figures.get("G");
    	RavensFigure figA=figures.get("A");
    	RavensFigure figF=figures.get("F");
    	RavensFigure figH=figures.get("H");
    	double union1=combinationPixel(figE, figG,1);
    	double union2=combinationPixel(figA, figF,1);
    	double single1=imagePixel(figC);
    	double single2=imagePixel(figH);
    	if(Math.abs(single1-union1)<0.05*union1){
    		if(Math.abs(single2-union2)<0.05*union2){
    			flag=true;
    		}
    	}
    	return flag;
    }
    private boolean twoShapesUnionToOne2(HashMap<String,RavensFigure> figures){
    	boolean flag=false;
    	RavensFigure figC=figures.get("C");
    	RavensFigure figE=figures.get("E");
    	RavensFigure figG=figures.get("G");
    	RavensFigure figA=figures.get("A");
    	RavensFigure figF=figures.get("F");
    	RavensFigure figH=figures.get("H");
    	
    	double union1=combinationPixel(figC, figG,1);
    	double union2=combinationPixel(figH, figF,1);
    	//System.out.println(combinationPixel(figF, figH,1));
    	double single1=imagePixel(figE);
    	double single2=imagePixel(figA);
    	//System.out.println(single2);
    	if(Math.abs(single1-union1)<0.05*union1){
    		//if(Math.abs(single2-union2)<0.05*union2){
    			flag=true;
    		//}
    	}
    	return flag;
    }
    private double twoSubtractionToOne(RavensFigure f1,RavensFigure f2,RavensFigure f3){
    	double percent=0.0;
    	percent=(imagePixel(f1)-imagePixel(f2))/imagePixel(f3);    	
    	return percent;
    } 
    private boolean twoShapesToOne(HashMap<String,RavensFigure> figures){
    	boolean flag=false;
    	RavensFigure figC=figures.get("C");
    	RavensFigure figE=figures.get("E");
    	RavensFigure figG=figures.get("G");
    	RavensFigure figH=figures.get("H");
    	RavensFigure figA=figures.get("A");
    	RavensFigure figF=figures.get("F");
    	double percent1=twoSubtractionToOne(figC,figE,figG);
    	double percent2=twoSubtractionToOne(figH,figA,figF);
    	if(Math.abs(percent1-percent2)<0.01){
    		flag=true;
    	}
    	return flag;
    }
    private boolean flip(String fig1,String fig2,HashMap<String,RavensFigure> figures){
    	boolean flag=false;
    	RavensFigure figA=figures.get(fig1);
    	RavensFigure figB=figures.get(fig2);
    	HashMap<String,RavensObject> obA=figA.getObjects();
		HashMap<String,RavensObject> obB=figB.getObjects();
		Set<String> obAkey=obA.keySet();
		Set<String> obBkey=obB.keySet();
		if(obA.size()==obB.size()){
			int totalAttrA=0;
			int totalAttrB=0;
			boolean tab=false;
			for(String k1:obAkey){
				RavensObject ro1=obA.get(k1);
				HashMap<String,String> attr1=ro1.getAttributes();
				totalAttrA+=attr1.size();
			}
			for(String k2:obAkey){
				RavensObject ro2=obA.get(k2);
				HashMap<String,String> attr2=ro2.getAttributes();
				totalAttrB+=attr2.size();
			}
			for(String k1:obAkey){
				RavensObject ro1=obA.get(k1);
				HashMap<String,String> attr1=ro1.getAttributes();
				Set<String> attrKey1=attr1.keySet();
				for(String k2:obBkey){
					RavensObject ro2=obB.get(k2);
					HashMap<String,String> attr2=ro2.getAttributes();
					Set<String> attrKey2=attr2.keySet();
					if(attr1.get("shape").equals(attr2.get("shape"))){
						if((attrKey1.contains("left-of"))&&(attrKey1.contains("angle"))&&!(attrKey2.contains("left-of"))&&(attrKey2.contains("angle"))){
							if(attr1.get("angle").equals(attr2.get("angle"))){
								tab=true;
							}
						}
					}	
				}
			}
			if((totalAttrB==totalAttrA)&&tab==true){
				flag=true;
			}
		}
    	return flag;
    }
    private boolean mirror(String fig1,String fig2,HashMap<String,RavensFigure> figures){
    	boolean flag=false;
    	RavensFigure figA=figures.get(fig1);
    	RavensFigure figB=figures.get(fig2);
    	HashMap<String,RavensObject> obA=figA.getObjects();
		HashMap<String,RavensObject> obB=figB.getObjects();
		Set<String> obAkey=obA.keySet();
		Set<String> obBkey=obB.keySet();
		if(obA.size()==obB.size()){
			int totalAttrA=0;
			boolean tab1=false;
			for(String k:obAkey){
				RavensObject ro1=obA.get(k);
				HashMap<String,String> attr=ro1.getAttributes();
				Set<String> attrKey=attr.keySet();
				if(attrKey.contains("left-of")&&attr.get("shape").equals("circle")){
					tab1=true;
				}
				totalAttrA+=attr.size();
			}
			int totalAttrB=0;
			boolean tab2=false;
			for(String k:obBkey){
				RavensObject ro2=obB.get(k);
				HashMap<String,String> attr=ro2.getAttributes();
				Set<String> attrKey=attr.keySet();
				if(attrKey.contains("left-of")&&attr.get("shape").equals("diamond")){
					tab2=true;
				}
				totalAttrB+=attr.size();
			}			
			if((totalAttrB==totalAttrA)&&tab1==true&&tab2==true){
				flag=true;
			}
			
		}
    	return flag;
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
    	double pixel1=imagePixel(figures.get(fig1));
    	double pixel2=imagePixel(figures.get(fig2));
    	double refPixel=pixel1-pixel2;
    	boolean result=false;
    	if(Math.abs(refPixel)<200){
    		result=true;
    	}
    	return result;
    }   
    private double shape(String fig1,String fig2,String fig3,HashMap<String,RavensFigure> figures,int type){
    	double pixel3=imagePixel(figures.get(fig3));
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
    	double pixel1=imagePixel(figures.get(fig1));
    	double pixel2=imagePixel(figures.get(fig2));
    	double pixel3=imagePixel(figures.get(fig3));
    	double refPixel=pixel1-pixel2;
    	double closestPixel=Double.MAX_VALUE;
    	int candidateAnswer=0;
    	for(int i=1;i<=type;i++){
    		double differencePixel=pixel3-imagePixel(figures.get(Integer.toString(i)));
    		if(Math.abs(differencePixel-refPixel)<closestPixel){
    			closestPixel=Math.abs(differencePixel-refPixel);
    			candidateAnswer=i;
    		}
    	}
    	return candidateAnswer;
    }
    private List getAnswerList(String fig1,String fig2,String fig3,HashMap<String,RavensFigure> figures,int type) throws IOException{
    	double pixel1=imagePixel(figures.get(fig1));
    	double pixel2=imagePixel(figures.get(fig2));
    	double pixel3=imagePixel(figures.get(fig3));
    	double refPixel=pixel1-pixel2;
    	List<Integer> answer=new ArrayList();;
    	double closestPixel=Double.MAX_VALUE;
    	int candidateAnswer=0;
    	for(int i=1;i<=type;i++){
    		double differencePixel=pixel3-imagePixel(figures.get(Integer.toString(i)));
    		if(Math.abs(Math.abs(differencePixel)-Math.abs(refPixel))<100){
    			answer.add(i);
    		}
    	}
    	return answer;
    }
    private double imagePixel(List<RavensFigure> figs,boolean flag){
    	double result=0.0;
    	for(RavensFigure f:figs){
    		result+=flag?imagePixel(f):-1*imagePixel(f);
    	}
    	
    	return result;
    }
    private double imagePixel(RavensFigure f){   	
		BufferedImage figureImage=null;
		double pixel=0;
		try {
			figureImage = ImageIO.read(new File(f.getVisual()));
			int color=0;
			for(int i=0;i<figureImage.getWidth();i++){
		    	for(int j=0;j<figureImage.getHeight();j++){
		    		color+=figureImage.getRGB(i, j)!=-1?1:0;
		    	}
			}
			pixel=(double)color;
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return pixel;
    }
    private double imagePixel2(BufferedImage image){ 
    	double pixel=0.0;
		int color=0;
		for(int i=0;i<image.getWidth();i++){
		    for(int j=0;j<image.getHeight();j++){
		    	color+=image.getRGB(i, j)!=-1?1:0;
		    }
		}
		pixel=(double)color;	
		return pixel;
    }
    private boolean sameFigs(String fig1,String fig2,HashMap<String,RavensFigure> figures){
    	boolean flag=false;
    	double dif=0.0;
    	double pixel1=imagePixel(figures.get(fig1));
    	double pixel2=imagePixel(figures.get(fig2));
    	dif=Math.abs(pixel1-pixel2);
    	if(dif<1.0){
    		flag=true;
    	}
    	return flag;
    }
}
