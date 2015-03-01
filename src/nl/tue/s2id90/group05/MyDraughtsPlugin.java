/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.s2id90.group05;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import nl.tue.s2id90.draughts.DraughtsPlayerProvider;
import nl.tue.s2id90.draughts.DraughtsPlugin;
import nl.tue.s2id90.group05.players.AdvancedAIPlayer;
import nl.tue.s2id90.group05.players.DumbAIPlayer;
import nl.tue.s2id90.group05.players.PositionAIPlayer;
import nl.tue.s2id90.group05.players.SimpleAIPlayer;



/**
 *
 * @author huub
 */
@PluginImplementation
public class MyDraughtsPlugin extends DraughtsPlayerProvider implements DraughtsPlugin {
    public MyDraughtsPlugin() {
        // make two players available to the AICompetition tool
        // During the final competition you should make only your 
        // best player available. For testing it might be handy
        // to make more than one player available.
        super(
            new UninformedPlayer(), 
            new OptimisticPlayer(), 
            new StupidPlayer(), 
            new DumbAIPlayer(), 
            new SimpleAIPlayer(), 
            new SimpleAIPlayer(), 
            new AdvancedAIPlayer(), 
            new AdvancedAIPlayer(), 
            new PositionAIPlayer(), 
            new PositionAIPlayer()
        );
    }
}
