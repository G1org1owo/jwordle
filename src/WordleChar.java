public class WordleChar {
    private char chr;
    private boolean present = false;
    private boolean exact = false;

    public WordleChar(char chr) {
        this.chr = chr;
    }

    public WordleChar(char chr, boolean present, boolean exact) {
        this.chr = chr;
        this.present = present;
        this.exact = exact;
    }

    public char getChr() {
        return chr;
    }
    public WordleChar setChr(char chr) {
        this.chr = chr;
        return this;
    }

    public boolean isPresent() {
        return present;
    }
    public WordleChar setPresent(boolean present) {
        this.present = present;
        return this;
    }

    public boolean isExact() {
        return exact;
    }
    public WordleChar setExact(boolean exact) {
        this.exact = exact;
        return this;
    }

    @Override
    public String toString() {
        return chr + (exact?
                "#" : present?
                "+" :
                "-"
                );
    }
}
