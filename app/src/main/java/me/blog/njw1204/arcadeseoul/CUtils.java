package me.blog.njw1204.arcadeseoul;

public class CUtils {
    static public String SearchableString(String string){
        return string.replaceAll("\\s", "").toLowerCase();
    }
}
