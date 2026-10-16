package week7;
interface CharacterDefense {
    int getArmorRating();
}

class BasicTunic implements CharacterDefense {
    public int getArmorRating() {
        return 10;
    }
}

class IronShieldDecorator implements CharacterDefense {
    private CharacterDefense defense;

    public IronShieldDecorator(CharacterDefense defense) {
        this.defense = defense;
    }

    public int getArmorRating() {
        return defense.getArmorRating() + 25;
    }
}

class HelmetDecorator implements CharacterDefense {
    private CharacterDefense defense;

    public HelmetDecorator(CharacterDefense defense) {
        this.defense = defense;
    }

    public int getArmorRating() {
        return defense.getArmorRating() + 15;
    }
}

class MagicAuraDecorator implements CharacterDefense {
    private CharacterDefense defense;

    public MagicAuraDecorator(CharacterDefense defense) {
        this.defense = defense;
    }

    public int getArmorRating() {
        return defense.getArmorRating() * 2;
    }
}
