package com.designpattern;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

enum Color{
	RED,BLUE,GREEN
}
enum Size{
	LARGE,BIG,SMALL
}

class Product{
	
	Size size;
	
	Color color;
	
	
	public Product(Size size, Color color) {
		super();
		this.size = size;
		this.color = color;
	}
	public Size getSize() {
		return size;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
}

class ProductFilter {
	
	Stream<Product> filterByColor(List<Product> p,Color c){
		
		return p.stream().filter(x->x.color==c);
	}
	
	Stream<Product> filterBySize(List<Product> p,Size c){
		
		return p.stream().filter(x->x.size==c);
	}
	Stream<Product> filterBySizeAndColor(List<Product> p,Size s,Color c){
		
		return (p.stream().filter(x->x.size==s && x.color==c));
	}
}

interface Specifications<T>{

	boolean isSatisfied(T item);
}

class ColorSpecification implements Specifications<Product>{

	Color c;
	
	public ColorSpecification(Color c) {
		super();
		this.c = c;
	}

	@Override
	public boolean isSatisfied(Product i) {
		return i.color==c;
	}
	
}

class SizeSpecification implements Specifications<Product>{

	Size s;
	
	public SizeSpecification(Size s) {
		super();
		this.s = s;
	}

	@Override
	public boolean isSatisfied(Product item) {
		// TODO Auto-generated method stub
		return item.size==s;
	}
	
}

interface Filter<T>{
	Stream<T> filterBy(List<T> p,Specifications s);		
}

class BetterFilter implements Filter<Product>{

	@Override
	public Stream<Product> filterBy(List<Product> pr, Specifications s) {
		return pr.stream().filter(p->s.isSatisfied(p));
	}
	
}
public class SRP {

	public static void main(String[] args) {
		
		List l=new ArrayList<>();
		l.add(new Product(Size.BIG,Color.BLUE));
		l.add(new Product(Size.SMALL,Color.RED));
		l.add(new Product(Size.LARGE,Color.GREEN));
		
		ProductFilter pf = new ProductFilter();
		pf.filterByColor(l, Color.BLUE).forEach(p->System.out.println(p.color +" "+p.size));
		pf.filterBySize(l, Size.SMALL).forEach(p->System.out.println(p.color +" "+p.size));
		pf.filterBySizeAndColor(l, Size.BIG, Color.BLUE).forEach(p->System.out.println(p.color +" "+p.size));;
		BetterFilter bf = new BetterFilter();
		bf.filterBy(l, new ColorSpecification(Color.BLUE));
	}
}
