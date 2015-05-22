package demo

import argonaut._,Argonaut._
import scalaz._,Scalaz._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object EitherDemo {
  import JodaDateCodecs._

  case class Track(num: Int, title: String, duration: Int)
  case class Album(title: String, artist: String, releaseDate: Option[DateTime], tracks: List[Track])

  implicit def TrackCodec = CodecJson.derive[Track]
  implicit def AlbumCodec = CodecJson.derive[Album]

  def parseCatalog(jsonText: String): List[Album] = ???
}

object JodaDateCodecs {
  lazy val fmt = DateTimeFormat.forPattern("yyyy-MM-dd")
  implicit val EncodeJodaDate: EncodeJson[DateTime] = EncodeJson(d => jString(fmt.print(d)))
  implicit val DecodeJodaDate: DecodeJson[DateTime] = implicitly[DecodeJson[String]].map(fmt.parseDateTime) setName "org.joda.time.DateTime"
}

object EitherDemoData {
  val threeAlbums =
    """[
         { "title" : "Album1",
           "artist" : "Artist 1",
           "releaseDate" : "2015-03-01",
           "tracks" : [
                        {"num" : 1, "title" : "Track 1", "duration" : 90 },
                        {"num" : 2, "title" : "Track 2", "duration" : 90 },
                        {"num" : 3, "title" : "Track 3", "duration" : 90 },
                        {"num" : 4, "title" : "Track 4", "duration" : 90 }
                      ]
         },
         { "title" : "Album2",
           "artist" : "Artist 2",
           "releaseDate" : "2015-03-02",
           "tracks" : [
                        {"num" : 1, "title" : "Track 1", "duration" : 90 },
                        {"num" : 2, "title" : "Track 2", "duration" : 90 },
                        {"num" : 3, "title" : "Track 3", "duration" : 90 },
                        {"num" : 4, "title" : "Track 4", "duration" : 90 }
                      ]
         },
         { "title" : "Album3",
           "artist" : "Artist 1",
           "releaseDate" : "2015-03-01",
           "tracks" : [
                        {"num" : 1, "title" : "Track 1", "duration" : 90 },
                        {"num" : 2, "title" : "Track 2", "duration" : 90 },
                        {"num" : 3, "title" : "Track 3", "duration" : 90 },
                        {"num" : 4, "title" : "Track 4", "duration" : 90 }
                      ]
         }
       ]
   """
}
