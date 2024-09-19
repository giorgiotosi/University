package it.univr.file;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DirectoryComponent extends AbstractComponent {
	// aggiungete campi, se servissero
	Component[] children;
	private List<Component> listChildren = new ArrayList<>();
	private List<FileComponent> files = new ArrayList<>();
	private int size = 0;
	//List<Component> components = new ArrayList<>();
	/**
	 * Costruisce una componente di tipo directory con il nome indicato
	 * e le sottocomponenti (figli) indicate.
	 */
	public DirectoryComponent(String name, Component... children) {
		super(name);
		for(Component child: children)
			listChildren.add(child);
		
		 this.children = new Component[children.length];
		for(int i = 0; i<children.length; i++) {
			this.children[i] = listChildren.get(i);
		}
		Arrays.sort(this.children, Comparator.comparing(Component::getName));
	}
	@Override
	public String toString(String nesting) {
		String result = "";
		
		String nextNesting = nesting + "    ";
		for(Component component: ((DirectoryComponent)this).children) {
			
			if(component instanceof FileComponent) {
				result += component.toString(nextNesting)+ "\n" ;
			}
			
			if(component instanceof DirectoryComponent) {
				result += component.toString(nextNesting);
			}
		}

		return nesting + this.getName() +"/"+ "\n"  +result;
		
	}
	@Override
	public int size() {
		//size += 100;
		// TODO Auto-generated method stub
		for(Component component: this.children) {
			if(component instanceof FileComponent)
				size += ((FileComponent)component).size();
				
			if(component instanceof DirectoryComponent)
				size += component.size();
		}
		return 100 + size;
	}
	@Override
	public List<FileComponent> getFiles() {
		// TODO Auto-generated method stub
		
		for(Component component: this.children) {
			if(component instanceof FileComponent)
				files.add((FileComponent) component);
			if(component instanceof DirectoryComponent)
				files.addAll(component.getFiles());
		}
		
		
		return files;
	}
	

	
	@Override
	public String find(String name) throws FileNotFoundException {
		for(Component child: children) {
			try {
				return getName() + "/" + child.find(name);
			}
			catch(FileNotFoundException e) {}
		}
		
		throw new FileNotFoundException(name);
	
	}
	

	
	
	

	// implementate sotto i metodi public ancora astratti
}