/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.File;
import java.util.List;
import java.util.Map;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public interface NewInterface {

    List<ChannelContainer> loadImages(List<ChannelContainer> channels);

    List<ChannelContainer> loadImages(Map<File, List<Integer>> channelMap);

}
