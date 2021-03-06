package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Boxer extends Player {
	private int doubleDamageCount = 0;
	private int rollMod = 0;
	private final static int MAX_TOKEN_USE = 1;

	public Boxer() {
		super(5, 2);
	}
	
	public Boxer(Boxer toCopy) {
		super(toCopy);
		this.doubleDamageCount = toCopy.doubleDamageCount;
		this.rollMod = toCopy.rollMod;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Boxer) {
			Boxer b = (Boxer)o;
			return super.equals(b) 
					&& b.doubleDamageCount == this.doubleDamageCount
					&& b.rollMod == this.rollMod;
		}
		
		return false;
	}

	@Override
	public Player clone() {
		return new Boxer(this);
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<>();
		
		actions.add(new Action((game) -> {}));
		
		for (int i = 1; i <= Math.min(MAX_TOKEN_USE, tokens); ++i) {
			final int TOKENS_USED = i;
			
			switch (pNum) {
			case 1:
				actions.add(new Action((game) -> {
					Boxer b = (Boxer)game.p1;
					b.doubleDamageCount += TOKENS_USED;
					b.addTokens(-TOKENS_USED);
				}));
				break;
			case 2:
				actions.add(new Action((game) -> {
					Boxer b = (Boxer)game.p2;
					b.doubleDamageCount += TOKENS_USED;
					b.addTokens(-TOKENS_USED);
				}));
			}
		}
		
		return actions;
	}

	@Override
	public void onPreRoll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPostRoll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPreTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPostTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPreDamage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPostDamage() {
		if (this.rollMod != 0) {
			rollMod = 0;
		}
		
		if (this.doubleDamageCount > 0) {
			this.doubleDamageCount = 0;
			this.rollMod -= 3 * this.doubleDamageCount;
		}
	}

	@Override
	public String getName() {
		return "Boxer";
	}

	@Override
	public int damageValue() {
		if (this.strengthValue() > opponent.strengthValue()) {
			int damage = 1;
			for (int a = 0; a < this.doubleDamageCount; ++a) {
				damage *= 2;
			}
			return damage;
		} else {
			return 0;
		}
	}
	
	@Override
	public int strengthValue() {
		return roll - rollMod;
	}
}
