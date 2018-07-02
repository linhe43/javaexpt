
package gc;
 
/**
 * 
 * @author yli
 *
 */
public class FinalizeDemo {
 
	private static FinalizeDemo obj;
 
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize method is excuted!");
		obj = this;
	}
 
	public static void main(String[] args) throws InterruptedException {
		obj = new FinalizeDemo();
 
		obj = null;
		System.gc();
 
		Thread.sleep(500);
		if (null != obj) {
			System.out.println("1-obj is still alive");
		} else {
			System.out.println("1-obj is dead");
		}
 
		obj = null;
		System.gc();
 
		Thread.sleep(500);
		if (null != obj) {
			System.out.println("2-obj is still alive");
		} else {
			System.out.println("2-obj is dead");
		}
	}
}
