package net.engdy.spacecadetstimer

/**
 * Copyright (c) 2026 Andy Foulke. All rights reserved.
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

enum class Phase(val img: Int, val duration: Long) {
    CONFIG(0, 0L),
    NEMESIS_START(0, 0L),
    TUTORIALS(0, 0L),
    DISCUSS_1(R.drawable.sc_step_1, 180_000L),
    SCIENCE_1_2(R.drawable.sc_step_1_2, 30_000L),
    PREPARE_2(R.drawable.sc_step_2, 0L),
    ACTION_3(R.drawable.sc_step_3, 30_000L),
    ACTION_3A(R.drawable.sc_step_3a, 30_000L),
    ACTION_3B(R.drawable.sc_step_3b, 30_000L),
    RESOLUTION_4(R.drawable.sc_step_4, 0L),
    TRACTOR_BEAM_5(R.drawable.sc_step_5, 30_000L),
    FIRE_WEAPONS_6(R.drawable.sc_step_6, 30_000L),
    ENEMY_NEMESIS_7(R.drawable.sc_step_7, 0L),
    JUMP_8(R.drawable.sc_step_8, 30_000L),
    REPAIR_9(R.drawable.sc_step_9, 30_000L),
    README(0, 0)
}
