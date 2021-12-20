package id.luthfisolahudin.smkn4.pbo.todolist.core;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Configuration {
    private static final Dotenv env = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String homePath() {
        return env.get(
                "USER_HOME_PATH",
                System.getProperty("user.home")
        );
    }

    public static String applicationDirectory() {
        String appDirName = env.get(
                "APPLICATION_DIRECTORY",
                "id.luthfisolahudin.smkn4.pbo.todolist"
        );
        File appDir = new File(homePath(), appDirName);

        if (!appDir.exists() || !appDir.isDirectory()) appDir.mkdirs();

        return appDirName;
    }

    public static String databaseName() {
        return env.get(
                "DATABASE_NAME",
                "todolist.db"
        );
    }

    public static String databasePath() {
        return env.get(
                "DATABASE_PATH",
                Paths.get(homePath(), applicationDirectory(), databaseName()).toString()
        );
    }

    public static String databaseTableName() {
        return env.get(
                "DATABASE_TABLE_NAME",
                "todos"
        );
    }
}
