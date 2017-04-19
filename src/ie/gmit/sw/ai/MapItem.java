package ie.gmit.sw.ai;

public class MapItem extends Sprite{
	
	private char ch;

	public MapItem(String name, String images) throws Exception {
		super(name, images);
		// TODO Auto-generated constructor stub
	}
	
	public MapItem(char ch, String name, String... images) throws Exception {
		super(name, images);
		this.ch=ch;
	}
	
	public char getChar()
	{
		return ch;
	}

}
