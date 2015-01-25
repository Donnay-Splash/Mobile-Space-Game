package com.example.controller;

import com.example.model.Player;

public interface GameEventListener {
	public void onPlayerDeath(Player player);
}
