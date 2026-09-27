package cometa.xyz.commands;

import cometa.xyz.utils.FriendCommand;
import cometa.xyz.utils.ParseCommand;
import cometa.xyz.utils.BotCommand;
import cometa.xyz.utils.ConfigCommand;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public final class CommandManager {
   private static final boolean f1 = false;

   private CommandManager() {
   }

   public static boolean m20(String var0) {
      return FriendCommand.m20(var0) || ConfigCommand.m20(var0) || BotCommand.m20(var0) || m374(var0);
   }

   public static List<String> m428(String var0) {
      String var1 = var0.stripLeading().toLowerCase(Locale.ROOT);
      if (".".equals(var1)) {
         return Stream.of(".friend", ".cfg", ".bot")
            .sorted()
            .toList();
      } else {
         return var1.startsWith(".p") && ".parse".startsWith(var1)
            ? List.of(".parse")
            : Stream.of(FriendCommand.m428(var0).stream(), ConfigCommand.m428(var0).stream(), BotCommand.m428(var0).stream(), Stream.empty())
               .flatMap(var0x -> (Stream<String>)var0x)
               .sorted(Comparator.naturalOrder())
               .toList();
      }
   }

   private static boolean m374(String var0) {
      if (!".parse".equalsIgnoreCase(var0.strip())) {
         return false;
      } else {
         ParseCommand.m63();
         return true;
      }
   }

   public static String m223(String var0) {
      List var1 = m428(var0);
      if (var1.isEmpty()) {
         return null;
      } else {
         String var2 = (String)var1.getFirst();
         if (".friend".equals(var2)
            || ".cfg".equals(var2)
            || ".bot".equals(var2)
            || ".neuro".equals(var2)
            || ".нейро".equals(var2)) {
            return var2 + " ";
         } else {
            return var2.endsWith("<nick>")
               ? var2.substring(0, var2.length() - "<nick>".length())
               : var2;
         }
      }
   }
}
