package telegramm.services;

/**
 * create time 07.03.2018
 *
 * @author nponosov
 */
public class MapDeserializationService {

    public static String getMapDeserialization(String mapString){
        return mapString
                .replace("1", Emoji.GRASS.toString())
                .replace("2", Emoji.BLACK_SQUARE.toString())
                .replace("0", Emoji.WHITE_SQUARE.toString())
                .replace("3", Emoji.RABBIT_FACE.toString())
                .replace("4", Emoji.WALL.toString());
    }
}
