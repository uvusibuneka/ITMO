import Exceptions.HandOverloadException;
import Status.*;


public class Main {
    public static void main(String[] args) {
        class Story{
            public void run(){
                //Дригль хмуро взглянул на Незнайку и распахнул перед ним дверь: Видя, что Незнайка хочет что-то сказать, он угрожающе взмахнул резиновой дубинкой и прокаркал, словно ворона: Сообразив, что разговоры действительно не принесут пользы. Незнайка махнул рукой и вышел за дверь.
                //Такелажным отделением, или попросту каталажкой, как ее окрестили сами арестованные, в полицейском управлении называлась огромная комната, напоминавшая по своему виду корабельную кладовую, где на многочисленных полках хранились различные корабельные снасти, обычно именуемые такелажем. Разница была лишь в том, что на полках здесь лежали не корабельные снасти, а обыкновенные коротышки. Посреди каталажки стояла чугунная печь, от которой через все помещение тянулись длинные жестяные трубы. Вокруг печки сидели несколько коротышек и пекли в горячей золе картошку. Время от времени кто-нибудь из них открывал чугунную дверцу, вытаскивал из золы испеченную картошку и начинал усиленно дуть на нее, перебрасывая с руки на руку, чтоб поскорей остудить. Другие коротышки сидели на полках или попросту на полу и занимались каждый своим делом: кто, вооружившись иглой, штопал свою ветхую одежонку, кто играл с приятелями в расшибалочку или рассказывал желавшим послушать какую-нибудь грустную историю из своей жизни. Помещение было без окон и освещалось одной-единственной электрической лампочкой, висевшей высоко под потолком. Лампочка была тусклая и светила, как говорится, только себе под нос. Как только Незнайка попал в каталажку и дверь за ним захлопнулась, он принялся протирать руками глаза, пытаясь хоть что-нибудь разглядеть в полутьме. Толку из этого вышло мало: он лишь размазал по лицу черную краску, которой были испачканы его руки. Увидев новоприбывшего, несколько самых любопытных коротышек соскочили со своих полок и подбежали к нему. Незнайка в испуге попятился и, прижавшись спиной к двери, приготовился защищаться. Разглядев его измазанную физиономию, коротышки невольно рассмеялись. Незнайка понял, что бояться не надо, и его лицо тоже расплылось в улыбке. Громкий смех заглушил его слова. Все хорошо знали, что фертинги -- это не что иное, как деньги, поэтому Незнайкины слова были сочтены за остроумную шутку. Он был без рубашки. Как раз в тот момент, когда Незнайка вошел, он зашивал на рубашке дырку, и теперь так и стоял с иголкой в руке.
                //
                Place outside = new Place("Снаружи", new Location(0, 0, 0));
                Person Neznaika = new Person("Незнайка", outside.cord);
                Person Drigl = new Person("Дригль", outside.cord);


                outside.add(Neznaika);
                outside.add(Drigl);

                Room room = new Room("Каталажка", new Location(1, 1, 1));
                room.open(Drigl);
                Drigl.lookAt(Neznaika);


                try {
                    Drigl.handR.hold(new Subject("Резиновая дубинка"));
                } catch (HandOverloadException e) {
                    System.out.println(e.getMessage());
                }

                Drigl.waveHand(Drigl.handR);
                Drigl.croak("*угрожающее карканье*");

                Neznaika.lookAt(Drigl);
                Neznaika.waveHand(Neznaika.handR);
                Clothes Shirt = new Clothes("Рубашка", ClothesType.ForArms);
                Spine spineN = new Spine("Игла Незнайки");
                Shirt.addHole(Shirt.new Hole(3));
                try {
                    Neznaika.hold(Neznaika.handL, Shirt);
                } catch (HandOverloadException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    Neznaika.hold(Neznaika.handR, spineN);
                } catch (HandOverloadException e) {
                    throw new RuntimeException(e);
                }
                Neznaika.repairClothes(Shirt, spineN);
                Neznaika.enter(room);
                room.close();
                Neznaika.repairClothes(Shirt, spineN);
                Neznaika.repairClothes(Shirt, spineN);

                Subject Tube = new Subject("Труба1");
                Subject Tube2 = new Subject("Труба2");
                Subject Tube3 = new Subject("Труба3");

                Subject Shelf = new Subject("Полка");
                Subject Shelf2 = new Subject("Полка2");

                room.add(Tube);
                room.add(Tube2);
                room.add(Tube3);

                room.add(Shelf);
                room.add(Shelf2);

                Shorty[] shorty = new Shorty[6];
                for (int i = 0; i < 6; i++) {
                    Shorty sh = new Shorty("Коротышка" + i);
                    shorty[i] = sh;
                    room.add(sh);
                }


                for (int i = 0; i < 6; i++) {
                    if (i < 3) {
                        shorty[i].sit(Tube);
                    } else if (i < 5) {
                        shorty[i].sit(Shelf);
                    } else {
                        shorty[i].sit(room.getFloor());
                    }
                }


                Stove stove = new Stove("Печка", new Location(2, 2, 2));

                room.add(stove);
                stove.open();

                Potato potato = new Potato("Картошка");
                stove.add(potato);
                stove.close();
                stove.toCook();

                try {
                    shorty[0].hold(shorty[0].handR, potato);
                } catch (HandOverloadException e) {
                    System.out.println(e.getMessage());
                }
                shorty[0].jugglingForCooling(potato);

                //Другие коротышки сидели на полках или попросту на полу и занимались каждый своим делом: кто, вооружившись иглой, штопал свою ветхую одежонку, кто играл с приятелями в расшибалочку или рассказывал желавшим послушать какую-нибудь грустную историю из своей жизни.
                Spine spine = new Spine("Игла");
                Clothes clothes = new Clothes("Одежонка", ClothesType.ForBody);
                shorty[1].repairClothes(clothes, spine);

                try {
                    shorty[1].hold(shorty[1].handR, spine);
                } catch (HandOverloadException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    shorty[1].hold(shorty[1].handL, clothes);
                } catch (HandOverloadException e) {
                    System.out.println(e.getMessage());
                }

                shorty[2].playRashibalochka();
                shorty[3].playRashibalochka();
                shorty[4].playRashibalochka();
                shorty[5].tellSadHistory();


                //Лампочка была тусклая и светила, как говорится, только себе под нос.
                Lamp lamp = new Lamp("Лампочка", 1);
                room.add(lamp);
                lamp.turnOn();
                Neznaika.getHandL().wipe(Neznaika.getFace());
                Neznaika.getHandR().wipe(Neznaika.getFace());

                // Увидев новоприбывшего, несколько самых любопытных коротышек соскочили со своих полок и подбежали к нему.
                // Незнайка в испуге попятился и, прижавшись спиной к двери, приготовился защищаться. Разглядев его измазанную физиономию,
                // коротышки невольно рассмеялись. Незнайка понял, что бояться не надо, и его лицо тоже расплылось в улыбке.
                // Громкий смех заглушил его слова. Все хорошо знали, что фертинги -- это не что иное, как деньги, поэтому Незнайкины слова были сочтены за остроумную шутку. Он был без рубашки.
                // Как раз в тот момент, когда Незнайка вошел, он зашивал на рубашке дырку, и теперь так и стоял с иголкой в руке.

                shorty[0].standUp();
                shorty[1].standUp();
                shorty[2].standUp();
                Neznaika.moveBack();
                for (int i = 0; i < shorty.length; i++) {
                    shorty[i].laugh();
                }

            }
        }
        Story story = new Story();
        story.run();

    }

}