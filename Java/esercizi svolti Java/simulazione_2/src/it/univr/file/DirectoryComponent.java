package it.univr.file;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoryComponent extends AbstractComponent {
	// aggiungete campi, se servissero
	Component[] children;
	/**
	 * Costruisce una componente di tipo directory con il nome indicato
	 * e le sottocomponenti (figli) indicate.
	 */
	public DirectoryComponent(String name, Component... children) {
		super(name);
		this.children = new Component[children.length];
		this.children = children;

		Arrays.sort(children, this::compare);
		// completate
	}

	private int compare(Component child1, Component child2) {
		return child1.getName().compareTo(child2.getName());
	}

	@Override
	public String toString(String nesting) {
		// TODO Auto-generated method stub
		String result = "";
		result += nesting + getName() + "/" + "\n";
		for(Component child: children) {
			if(child instanceof FileComponent)
				result += child.toString(nesting +"  ") + "\n";
			else
				result += child.toString(nesting +"  ");
		}
		return result;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		int size = 0;
		for(Component child: children) {
			size += child.size();
		}
		return size + 100;
	}

	@Override
	public List<FileComponent> getFiles() {
		List<FileComponent> files = new ArrayList<>();
		for(Component child: children) {
			files.addAll(child.getFiles());
		}
		return files;
	}

	@Override
	public String find(String name) throws FileNotFoundException {
		for(Component child: children) {
		try {
				return getName() + "/" + child.find(name);
			} catch (FileNotFoundException e) {}
		}
		throw new FileNotFoundException(name);

	}
	// implementate sotto i metodi public ancora astratti
}