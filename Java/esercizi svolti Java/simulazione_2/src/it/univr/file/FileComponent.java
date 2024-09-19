package it.univr.file;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileComponent extends AbstractComponent {
	// aggiungete campi, se servissero
	private final int size;
	

	/**
	 * Costruisce una componente di tipo file con il nome indicato
	 * e la dimensione in byte indicata.
	 */
	public FileComponent(String name, int size) {
		// completate
		super(name);
		this.size = size;
	}

	@Override
	public String toString(String nesting) {
		// TODO Auto-generated method stub
		return nesting + getName();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public List<FileComponent> getFiles() {
		// TODO Auto-generated method stub
		 List<FileComponent> list = new ArrayList<>();
		 list.add(this);
		return list;
	}

	@Override
	public String find(String name) throws FileNotFoundException {
		if(getName().equals(name)) {
			return getName();
		}
		else
			throw new FileNotFoundException(name);
	}



	// implementate sotto i metodi public ancora astratti
}