package etapa4.apresentacao;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Small UI helper utilities used across the presentation screens.
 */
public class TelaUtils {

    /**
     * Show a selection dialog with the toString() of the provided objects and
     * return the first integer found inside the selected item's string.
     * This is a generic helper so callers don't need per-class extraction logic.
     *
     * @param parent parent component (may be null)
     * @param title dialog title
     * @param items list of objects to present (uses toString())
     * @return selected integer id or null when user cancels or no selection
     */
    public static Integer selectIdFromObjects(Component parent, String title, List<?> items) {
        if (items == null || items.isEmpty()) return null;

        String[] display = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            display[i] = items.get(i).toString();
        }

        JComboBox<String> combo = new JComboBox<>(display);
        int option = JOptionPane.showConfirmDialog(parent, combo, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION) return null;

        int sel = combo.getSelectedIndex();
        if (sel < 0 || sel >= display.length) return null;

        return parseFirstInt(display[sel]);
    }

    private static Integer parseFirstInt(String text) {
        if (text == null) return null;
        Pattern p = Pattern.compile("\\b(\\d+)\\b");
        Matcher m = p.matcher(text);
        if (m.find()) {
            try {
                return Integer.parseInt(m.group(1));
            } catch (NumberFormatException ex) {
                return null;
            }
        }
        return null;
    }

    /**
     * Generic selection helper where caller provides a mapping to extract the id from each item.
     */
    public static <T> Integer selectId(Component parent, String title, java.util.List<T> items, java.util.function.Function<T, Integer> idMapper) {
        // Backwards-compatible overload: use toString() as display
        return selectId(parent, title, items, idMapper, Object::toString);
    }

    /**
     * Select an id from a list providing both an idMapper and displayMapper for concise UI entries.
     */
    public static <T> Integer selectId(Component parent, String title, java.util.List<T> items, java.util.function.Function<T, Integer> idMapper, java.util.function.Function<T, String> displayMapper) {
        if (items == null || items.isEmpty()) return null;
        String[] display = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            String label = displayMapper.apply(items.get(i));
            display[i] = label != null ? label : items.get(i).toString();
        }

        JComboBox<String> combo = new JComboBox<>(display);
        int option = JOptionPane.showConfirmDialog(parent, combo, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION) return null;

        int sel = combo.getSelectedIndex();
        if (sel < 0 || sel >= items.size()) return null;

        return idMapper.apply(items.get(sel));
    }
}
