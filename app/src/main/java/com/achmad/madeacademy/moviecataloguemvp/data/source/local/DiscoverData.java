package com.achmad.madeacademy.moviecataloguemvp.data.source.local;

import com.achmad.madeacademy.moviecataloguemvp.data.Movie;

import java.util.ArrayList;

public class DiscoverData {
    private String[][] movie = new String[][]{
            {"Fast & Furious Presents: Hobbs & Shaw",
                    "August 2, 2019",
                    "65",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/keym7MPn1icW1wWfzMnW3HeuzWU.jpg",
                    "A spinoff of The Fate of the Furious, focusing on Johnson's US Diplomatic Security Agent Luke Hobbs forming an unlikely alliance with Statham's Deckard Shaw.",
                    "Dwayne Johnson",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/kuqFzlYMc2IrsOyPznMd1FroeGq.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/qAhedRxRYWZAgZ8O8pHIl6QHdD7.jpg",
            },
            {"Angel Has Fallen ",
                    "August 23, 2019",
                    "57",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/fapXd3v9qTcNBTm39ZC4KUVQDNf.jpg",
                    "Secret Service Agent Mike Banning is framed for the attempted assassination of the President and must evade his own agency and the FBI as he tries to uncover the real threat.",
                    "Gerard Butler",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/wRKkVe5uugkx4KnmphBSjJUWPTo.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/7uCHvw3j0G5ns5X2rWuU1BXRmoJ.jpg",
            },
            {"It Chapter Two",
                    "September 6, 2019",
                    "72",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/zfE0R94v1E8cuKAerbskfD3VfUt.jpg",
                    "27 years after overcoming the malevolent supernatural entity Pennywise, the former members of the Losers' Club, who have grown up and moved away from Derry, are brought back together by a devastating phone call.",
                    "James Mcavoy",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/oPIfGm3mf4lbmO5pWwMvfTt5BM1.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/4W0FnjSGn4x0mKZlBRx8OjFxQUM.jpg",
            },
            {"The Caged Flower",
                    "November 23, 2013",
                    "62",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/tltTPQeVK2XF3J0CgMc6kOzdF91.jpg",
                    "Yoriko Jun turned Miyuki Fukashi’s sensual novel with the same name into a movie. An ordinary office lady and a boy meet on the Internet, they get into a master-slave relationship and are drowned in a world of pleasure.",
                    "Misagi Morino",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/hKfbLDhIMHyyhEjwkQpO0CRxngp.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/6g0weS4HxtGjPYvSYFlPv1NAopu.jpg",
            },
            {"Red Shoes and the Seven Dwarfs",
                    "August 1, 2019",
                    "51",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/MBiKqTsouYqAACLYNDadsjhhC0.jpg",
                    "Princes who have been turned into Dwarfs seek the red shoes of a lady in order to break the spell, although it will not be easy.",
                    "Chloë Grace Moretz",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/sk6we3WfYppsx5KjMVzQGYEXpD2.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/42JStePfDGIQi6dYWh5UsVpjG3c.jpg",
            },
            {"Hello, Love, Goodbye",
                    "July 31, 2019",
                    "55",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/kTca5fpKhFOKIiG0tz8tynhsWs5.jpg",
                    "A love story of Joy and Ethan, Filipino workers based in Hong Kong. Ethan, a bartender, is keen on romantically pursuing Joy, a domestic helper who is wholly dedicated to providing for her family.",
                    "Kathryn Bernardo",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/b2smlh77GUeJBogZ7BZsRQNgzsX.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/foMDjFHrTrex6Rr6bApB8TbWlie.jpg",
            },
            {"Fate/stay night: Heaven’s Feel II. lost butterfly",
                    "January 12, 2019",
                    "54",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/4tS0iyKQBDFqVpVcH21MSJwXZdq.jpg",
                    "Theatrical-release adaptation of the visual novel \"Fate/stay night\", following the third and final route. (Part 2 of a trilogy.)",
                    "Noriko Shitaya",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/3KXz8L2VzuUXBDiDyCrE6PmyIYy.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/fznBsxUOM8HjwpilQnWbrl9bt59.jpg",
            },
            {"The Dead Don't Die",
                    "June 14, 2019",
                    "56",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/fgGzTEoNxptCRtEOpOPvIEdlxAq.jpg",
                    "In a small peaceful town, zombies suddenly rise to terrorize the town. Now three bespectacled police officers and a strange Scottish morgue expert must band together to defeat the undead.",
                    "Selena Gomez",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/2vo27YF7XKZaTNCeY8ViVZWhsXd.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/nNI0W5z0OuJzsSZpi9x3El2HL2M.jpg",
            },
            {"Good Boys",
                    "August 16, 2019",
                    "66",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/jIthqo2tQmW8TFN1fYpF8FmVL0o.jpg",
                    "A group of young boys on the cusp of becoming teenagers embark on an epic quest to fix their broken drone before their parents get home.",
                    "Brady Noon",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/cbzmfWc6BK70WfXEgm7AVbnf6K5.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/zIZv4pPJRg3YTnWWuotwXnYu1fM.jpg",
            },
            {"John Wick",
                    "October 24, 2014",
                    "72",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/5vHssUeVe25bMrof1HyaPyWgaP.jpg",
                    "Ex-hitman John Wick comes out of retirement to track down the gangsters that took everything from him.",
                    "Keanu Reeves",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/bOlYWhVuOiU6azC4Bw6zlXZ5QTC.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/fINt8y7nDRUkxqAHZaO3GaTTkax.jpg",
            },
    };
    private String[][] tvShow = new String[][]{
            {"I Am Not an Animal",
                    "May 10, 2004",
                    "95",
                    "https://image.tmdb.org/t/p/w370_and_h556_bestv2/nMhv6jG5dtLdW7rgguYWvpbk0YN.jpg",
                    "I Am Not An Animal is an animated comedy series about the only six talking animals in the world, whose cosseted existence in a vivisection unit is turned upside down when they are liberated by animal rights activists.",
                    "Julia Davis",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/7JDDn3h1Ijj32lyggxvbxi7zBEX.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/ok1YiumqOCYzUmuTktnupOQOvV5.jpg",
            },
            {"Chernobyl",
                    "May  6, 2019",
                    "86",
                    "https://image.tmdb.org/t/p/w370_and_h556_bestv2/hlLXt2tOPT6RRnjiUmoxyG1LTFi.jpg",
                    "A dramatization of the true story of one of the worst man-made catastrophes in history, the catastrophic nuclear accident at Chernobyl. A tale of the brave men and women who sacrificed to save Europe from unimaginable disaster.",
                    "Jessie Buckley",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/h5X1PiejgVojip9UVqyb4g6so5A.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/cDpR6URVghK5LC7G7NgPkCP8x6E.jpg",
            },
            {"Hunter x Hunter",
                    "October  2, 2011",
                    "83",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/yWBcBIO9OrF3E85C5Arols6QNnG.jpg",
                    "Twelve-year-old Gon Freecss one day discovers that the father he had always been told was dead was alive and well. His Father, Ging, is a Hunter—a member of society's elite with a license to go anywhere or do almost anything. Gon, determined to follow in his father's footsteps, decides to take the Hunter Examination and eventually find his father to prove himself as a Hunter in his own right. But on the way, he learns that there is more to becoming a Hunter than previously thought, and the challenges that he must face are considered the toughest in the world.",
                    "Mariya Ise",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/4jG7Nsb57iF9LlnqGR4hVWKWTaj.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/lzibeUYh2iZkbG2y8LMnoIavbyC.jpg",
            },
            {"Avatar: The Last Airbender",
                    "February 21, 2005",
                    "83",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/sB8V0pQtJZ17v8FLXMOcYz6045c.jpg",
                    "In a war-torn world of elemental magic, a young boy reawakens to undertake a dangerous mystic quest to fulfill his destiny as the Avatar, and bring peace to the world.",
                    "Zach Tyler",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/iRltVn3JmHmjSi5jz12CHGTzH5s.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/v68G0YcUs4qZQrqd7ihj9WJD6M6.jpg",
            },
            {"Death Note",
                    "October 4, 2006",
                    "83",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/g8hHbsRmHYoWYizhWCk87vpkrmy.jpg",
                    "Light Yagami is an ace student with great prospects—and he’s bored out of his mind. But all that changes when he finds the Death Note, a notebook dropped by a rogue Shinigami death god. Any human whose name is written in the notebook dies, and Light has vowed to use the power of the Death Note to rid the world of evil. But will Light succeed in his noble goal, or will the Death Note turn him into the very thing he fights against?",
                    "Aya Hirano",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/eHnM9NL0WKm977YIrpmmuCyHu5k.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/nYvmzyNzay47P4XqGFK14NtrKI.jpg",
            },
            {"Fullmetal Alchemist: Brotherhood",
                    "April  5, 2009",
                    "82",
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/5ZFUEOULaVml7pQuXxhpR2SmVUw.jpg",
                    "Edward and Alphonse Elric's reckless disregard for alchemy's fun\u00ADdamental laws ripped half of Ed's limbs from his body and left Al's soul clinging to a cold suit of armor. To restore what was lost, the brothers scour a war-torn land for the Philosopher's Sto\u00ADne, a fabled relic which grants the ability to perform alchemy in impossible ways.\n" +
                            "\n" +
                            "The Elrics are not alone in their search; the corrupt State Military is also eager to harness the artifact's power. So too are the strange Homunculi and their shadowy creator. The mythical gem lures exotic alchemists from distant kingdoms, scarring some deeply enough to inspire murder. As the Elrics find their course altered by these enemies and allies, their purpose remains unchanged – and their bond unbreakable.",
                    "Rie Kugimiya",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/4k7CLf1yPWtwZRkieAPYuKzEl3L.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/c368lahfH9sgdDHKp6ds7EprIga.jpg",
            },
            {"Game of Thrones",
                    "April 17, 2011",
                    "81",
                    "https://image.tmdb.org/t/p/w370_and_h556_bestv2/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
                    "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                    "Emilia Clarke",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/g6jjDO1vW9K28jzo5z3q9xnZCTE.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/vDoZ7Naxbo6EOlm8fcpAPlvTeyE.jpg",
            },
            {"Justice League",
                    "November 17, 2001",
                    "81",
                    "https://image.tmdb.org/t/p/w370_and_h556_bestv2/1JszDEGLI6s5HjoGhe3VnfX2ez8.jpg",
                    "The long-awaited rebirth of the greatest superhero team of all time: Batman, Superman, The Flash, Wonder Woman, Hawkgirl, Green Lantern and Martian Manhunter.",
                    "George Newbern",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/kUFci5BzhMyx3ZNQEvpauRmpMyT.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/kAp8Clm9dN4aHq8ywJ7WXc2T5Se.jpg",
            },
            {"Silicon Valley",
                    "April  6, 2014",
                    "80",
                    "https://image.tmdb.org/t/p/w370_and_h556_bestv2/dc5r71XI1gD4YwIUoEYCLiVvtss.jpg",
                    "In the high-tech gold rush of modern Silicon Valley, the people most qualified to succeed are the least capable of handling success. Partially inspired by Mike Judge’s own experiences as a Silicon Valley engineer in the late ‘80s, Silicon Valley is an American sitcom that centers around six programmers who are living together and trying to make it big in the Silicon Valley.",
                    "Thomas Middleditch",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/e6rjipqOp5iPjMMdU9YgGy8ueI.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/8vk5R31KG5UQTAwTVEBxn65NSB.jpg",
            },
            {"My Hero Academia",
                    "April 3, 2016",
                    "79",
                    "https://image.tmdb.org/t/p/w370_and_h556_bestv2/47h1ReX6SSqwiTaz3kCC14f0vzt.jpg",
                    "In a world where eighty percent of the population has some kind of super-powered Quirk, Izuku was unlucky enough to be born completely normal. But that won’t stop him from enrolling in a prestigious hero academy. Now, he’ll get his first taste of brutal rivalry from other schools as he braves the cutthroat, no-holds-barred provisional license exam.",
                    "Ayane Sakura",
                    "https://image.tmdb.org/t/p/w138_and_h175_face/bgHCKok1dEhyhzGeual7hM7EsKA.jpg",
                    "https://image.tmdb.org/t/p/w533_and_h300_bestv2/r1jOpRyqP5pKkvZvaiCXVJ5RYOa.jpg",
            },
    };

    public ArrayList<Movie> getMovie() {

        ArrayList<Movie> list = new ArrayList<>();
        for (String[] aData : movie) {
            Movie movie = new Movie();
            movie.setTitle(aData[0]);
            movie.setRelease(aData[1]);
            movie.setUser_score(aData[2]);
            movie.setImg_poster(aData[3]);
            movie.setOverview(aData[4]);
            movie.setFeatured_crew(aData[5]);
            movie.setImg_featured_crew(aData[6]);
            movie.setImg_Backdrop(aData[7]);
            list.add(movie);
        }
        return list;
    }

    public ArrayList<Movie> getTvShow() {

        ArrayList<Movie> list = new ArrayList<>();
        for (String[] aData : tvShow) {
            Movie movie = new Movie();
            movie.setTitle(aData[0]);
            movie.setRelease(aData[1]);
            movie.setUser_score(aData[2]);
            movie.setImg_poster(aData[3]);
            movie.setOverview(aData[4]);
            movie.setFeatured_crew(aData[5]);
            movie.setImg_featured_crew(aData[6]);
            movie.setImg_Backdrop(aData[7]);
            list.add(movie);
        }
        return list;
    }

}
