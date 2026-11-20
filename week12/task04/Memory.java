public class Memory {
    public void load(long position, byte[] data) {
        System.out.println("Memory load " + data.length + " bytes at 0x" + Long.toHexString(position));
    }
}
