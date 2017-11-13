package betterquesting.api2.client.gui.controls;

public interface IValueIO<T>
{
	public T readValue();
	public void writeValue(T value);
}
