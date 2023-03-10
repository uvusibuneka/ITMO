import Exceptions.*;
import Status.*;

import java.util.ArrayList;


public class Person extends Entity implements Storage {
    protected ArrayList<Clothes> clothes;
    protected boolean isSitting = false;

    public void laugh() {
        System.out.println(this.getName() + " смеется");
    }

    public void enter(Room room) {
        System.out.println(this.getName() + " вошел в комнату " + room.getName());
        cord = room.getLocation();
    }

    public void sit(Subject subject) {
        System.out.println(this.getName() + " сидит на предмете " + subject.getName());
        this.isSitting = true;
    }

    public void standUp() {
        System.out.println(this.getName() + " встал");

    }

    public void putOn(Clothes clothes) {
        if (this.clothes.size() > 10) {
            throw new PersonOverloadException("Слишком много одежды");
        }
        System.out.println(this.getName() + " надел " + clothes.getName());
        this.clothes.add(clothes);

    }

    public void putOff(Clothes clothes) {
        System.out.println(this.getName() + " снял " + clothes.getName());
        this.clothes.remove(clothes);
    }

    public Person(String name, Location cord) {
        super(name, cord);
    }

    public Person(String name) {
        super(name);
    }

    public void waveHand(Hand hand) {
        System.out.println(this.getName() + " махнул рукой " + hand.getName());
    }

    public void joke() {
        System.out.println(this.getName() + " пошутил");
    }

    public void hold(Hand hand, Subject subject) throws HandOverloadException {
        System.out.println(this.getName() + " держит " + subject.getName() + " рукой " + hand.getName());
        hand.hold(subject);
    }

    public void repairClothes(Clothes clothes, Spine spine) {
        if (clothes.getHolesCount() == 0) {
            System.out.println(this.getName() + " попытался починить  " + clothes.getName());
            return;
        }
        Subject.Hole h = clothes.getHole(0);
        if (h.getVolume() > 0) {
            h.setVolume(h.getVolume() - 1);
            System.out.println(this.getName() + " чинит " + clothes.getName());
        } else {
            clothes.removeHole(0);
            System.out.println(this.getName() + " починил " + clothes.getName());
        }

    }

    public void takeOffClothes(Clothes clothes) {
        System.out.println(this.getName() + " снял " + clothes.getName());
        this.clothes.remove(clothes);
    }


    public void smile() {
        face.smile();
    }

    public void croak(String replic) {
        System.out.println(this.getName() + " прокрякал " + replic);
    }

    public void lookAt(Entity entity) {
        System.out.println(this.getName() + " посмотрел на " + entity.getName());
    }

    public void moveBack() {
        System.out.println(this.getName() + " попятился");
    }

    @Override
    public void add(Entity entity) {
        clothes.add((Clothes) entity);
    }

    @Override
    public void remove(Entity entity) {
        clothes.remove((Clothes) entity);
    }


    @Override
    public boolean contains(Entity entity) {
        return clothes.contains((Clothes) entity);
    }

    @Override
    public boolean isEmpty() {
        return clothes.isEmpty();
    }

    @Override
    public int size() {
        return clothes.size();
    }


    static protected class Bodypart extends Subject {
        public void touch(Subject subject) {
            System.out.println(this.name + " прикоснулся к " + subject.getName() + " c помощью " + this.getName());

            if (this.color.getColor() != subject.color.getColor()) {
                if (this.color.getColor() == ColorType.CLEAR)
                    this.color.setColor(subject.color.getColor());
                else if (subject.color.getColor() == ColorType.CLEAR) {
                    subject.color.setColor(this.color.getColor());
                    System.out.println("При касании с " + this.name + " " + subject.getName() + " окрасился в " + subject.color.getColor() + " цвет");
                } else {
                    this.color.setColor(ColorType.DIRTY);
                    subject.color.setColor(ColorType.DIRTY);
                    System.out.println("Цвета при соприкосновении " + this.name + " и " + subject.getName() + " смешались");
                }
            }
        }

        public Bodypart(String name) {
            super(name);
        }
    }

    static protected class Hand extends Bodypart implements Storage {
        ArrayList<Entity> objects = new ArrayList<Entity>();

        public Hand(String name) {
            super(name);
        }

        public void hold(Entity entity) throws HandOverloadException {

            if (this.size() < 2) {
                this.add(entity);
            } else {
                throw new HandOverloadException("Рука перегружена");
            }
        }

        //wipe
        public void wipe(Subject subject) {
            System.out.println(this.name + " вытер " + subject.getName() + " рукой " + this.getName());
            if (subject.color.getColor() == ColorType.DIRTY) {
                subject.color.setColor(ColorType.CLEAR);
                System.out.println("При вытирании " + subject.getName() + " стал чистым");
            }
        }

        public void release(Entity entity) {
            if (this.size() == 1) {
                this.remove(entity);
            } else {
                System.out.println(this.name + " пуста");
            }
        }

        public void wave(Hand hand) {
            System.out.println(this.name + "махнул рукой " + hand.getName());
        }

        @Override
        public void add(Entity entity) {
            objects.add(entity);
        }

        @Override
        public void remove(Entity entity) {
            objects.remove(entity);
        }

        @Override
        public boolean contains(Entity entity) {
            return objects.contains(entity);
        }


        @Override
        public boolean isEmpty() {
            return objects.isEmpty();
        }

        @Override
        public int size() {
            return 0;
        }
    }


    static protected class Face extends Bodypart {
        public Face(String name) {
            super(name);
        }

        public void smile() {
            System.out.println(this.name + " улыбнулся");
        }
    }


    protected Hand handL = new Hand("Левая рука " + this.getName());
    protected Hand handR = new Hand("Правая рука " + this.getName());
    protected Face face = new Face("Лицо " + this.getName());

    public Hand getHandL() {
        return handL;
    }

    public Hand getHandR() {
        return handR;
    }

    public Face getFace() {
        return face;
    }

}