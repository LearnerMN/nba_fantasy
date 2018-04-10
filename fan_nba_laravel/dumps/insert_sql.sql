INSERT INTO `laraveldb`.`contest`
(`con_id`,
`con_ych_code`,
`con_sport_code`,
`con_type`,
`con_title`,
`con_entry_fee`,
`con_entry_count`,
`con_entry_limit`,
`con_multiple_entry_limit`,
`con_total_prize`,
`con_salary_cap`,
`con_start_time`,
`con_multiple_entry`,
`con_scope`,
`con_guaranteed`,
`con_state`,
`created_at`,
`updated_at`)
SELECT `nba_contest`.`id`,
    `nba_contest`.`code`,
    `nba_contest`.`sportCode`,
    `nba_contest`.`type`,
    `nba_contest`.`title`,
    `nba_contest`.`entryFee`,
    `nba_contest`.`entryCount`,
    `nba_contest`.`entryLimit`,
    `nba_contest`.`multipleEntryLimit`,
    `nba_contest`.`totalPrize`,
    `nba_contest`.`salaryCap`,
    `nba_contest`.`startTime`,
    `nba_contest`.`multipleEntry`,
    `nba_contest`.`scope`,
    `nba_contest`.`guaranteed`,
    `nba_contest`.`state`,
    `nba_contest`.`created_at`,
    `nba_contest`.`updated_at`
FROM `homestead`.`nba_contest`;

INSERT INTO `laraveldb`.`entries`
(`ent_id`,
`ent_con_id`,
`ent_lu_id`,
`ent_user_id`,
`ent_score`,
`ent_winnings`,
`ent_pw_id`,
`ent_pef_id`,
`ent_canceled_at`,
`ent_cancelable`,
`ent_maximum_point`,
`ent_profitable_points`,
`ent_minimum_points`,
`ent_projected_points`,
`ent_periods_remaining`,
`ent_remaining_time_unit`,
`ent_total_time_unit`,
`created_at`,
`updated_at`)
SELECT `nba_contest_entry`.`id`,
    `nba_contest_entry`.`contest_id`,
    1,
    `nba_contest_entry`.`user_id`,
    `nba_contest_entry`.`score`,
    `nba_contest_entry`.`winnings`,
    1,1,
    `nba_contest_entry`.`canceledAt`,
    `nba_contest_entry`.`cancelable`,
    `nba_contest_entry`.`maximumPoints`,
    `nba_contest_entry`.`profitablePoints`,
    `nba_contest_entry`.`minimumPoints`,
    `nba_contest_entry`.`projectedPoints`,
    `nba_contest_entry`.`periodsRemaining`,
    `nba_contest_entry`.`remainingTimeUnit`,
    `nba_contest_entry`.`totalTimeUnit`,
    `nba_contest_entry`.`created_at`,
    `nba_contest_entry`.`updated_at`
FROM `homestead`.`nba_contest_entry`;

INSERT INTO `laraveldb`.`players`
(`player_id`,
`player_code`,
`player_firstname`,
`player_lastname`,
`player_sport_code`,
`player_number`,
`player_jersey_number`,
`player_status`,
`player_image_url`,
`player_large_image_url`,
`player_team_id`,
`player_salary`,
`player_original_salary`,
`player_projected_points`,
`player_starting`,
`player_pos_id`,
`player_primary_position`,
`player_eligible_position`,
`player_fantasy_points_per_game`,
`created_at`,
`updated_at`)
SELECT `nba_player`.`id`,
    `nba_player`.`code`,
    `nba_player`.`firstName`,
    `nba_player`.`lastName`,
    `nba_player`.`sportCode`,
    `nba_player`.`number`,
    `nba_player`.`jerseyNumber`,
    `nba_player`.`status`,
    `nba_player`.`imageUrl`,
    `nba_player`.`largeImageUrl`,
    `nba_player`.`team_id`,
    `nba_player`.`salary`,
    `nba_player`.`originalSalary`,
    `nba_player`.`projectedPoints`,
    `nba_player`.`starting`,
    1,
    `nba_player`.`primaryPosition`,
    `nba_player`.`eligiblePositions`,
    `nba_player`.`fantasyPointsPerGame`,
    `nba_player`.`created_at`,
    `nba_player`.`updated_at`
FROM `homestead`.`nba_player`;

INSERT INTO `laraveldb`.`matches`
(`mtc_id`,
`mtc_code`,
`mtc_y_code`,
`mtc_sport_code`,
`mtc_home_team_code`,
`mtc_away_team_code`,
`mtc_home_score`,
`mtc_away_score`,
`mtc_status_type`,
`mtc_status`,
`mtc_lineup_available`,
`mtc_start_time`,
`mtc_location`,
`mtc_weather`,
`mtc_boxscore_link`,
`created_at`,
`updated_at`)
SELECT `nba_contest_match`.`id`,
    `nba_contest_match`.`code`,
    `nba_contest_match`.`yCode`,
    `nba_contest_match`.`sportCode`,
    `nba_contest_match`.`homeTeamCode`,
    `nba_contest_match`.`awayTeamCode`,
    `nba_contest_match`.`homeScore`,
    `nba_contest_match`.`awayScore`,
    `nba_contest_match`.`statusType`,
    `nba_contest_match`.`status`,
    `nba_contest_match`.`lineupAvailable`,
    `nba_contest_match`.`startTime`,
    `nba_contest_match`.`location`,
    `nba_contest_match`.`weather`,
    `nba_contest_match`.`boxscoreLink`,
    `nba_contest_match`.`created_at`,
    `nba_contest_match`.`updated_at`
FROM `homestead`.`nba_contest_match`;

INSERT INTO `laraveldb`.`nba_player_stats`
(`nps_id`,
`nps_game_code`,
`nps_player_code`,
`nps_min`,
`nps_fpts`,
`nps_pt`,
`nps_threept`,
`nps_reb`,
`nps_ast`,
`nps_st`,
`nps_blk`,
`nps_to`,
`created_at`,
`updated_at`)
SELECT `nba_player_stats`.`id`,
    `nba_player_stats`.`game_id`,
    `nba_player_stats`.`player_id`,
    `nba_player_stats`.`min`,
    `nba_player_stats`.`fpts`,
    `nba_player_stats`.`pts`,
    `nba_player_stats`.`threept`,
    `nba_player_stats`.`reb`,
    `nba_player_stats`.`ast`,
    `nba_player_stats`.`st`,
    `nba_player_stats`.`blk`,
    `nba_player_stats`.`to`,
    `nba_player_stats`.`created_at`,
    `nba_player_stats`.`updated_at`
FROM `homestead`.`nba_player_stats`;

INSERT INTO `laraveldb`.`teams`
(`team_id`,
`team_code`,
`team_location`,
`team_name`,
`team_t_name`,
`abbr`,
`imageUrl`,
`created_at`,
`updated_at`)
SELECT `nba_team`.`id`,
    `nba_team`.`code`,
    `nba_team`.`location`,
    `nba_team`.`name`,
    `nba_team`.`teamName`,
    `nba_team`.`abbr`,
    `nba_team`.`imageUrl`,
    `nba_team`.`created_at`,
    `nba_team`.`updated_at`
FROM `homestead`.`nba_team`;

INSERT INTO `laraveldb`.`positions` (`pos_key`, `pos_abbr`, `pos_name`, `pos_sport_code`, `pos_eligible_positions`, `created_at`, `updated_at`) VALUES ('PG', 'PG', 'Point Guard', 'nba', 'PG', NOW(), NOW());
INSERT INTO `laraveldb`.`positions` (`pos_key`, `pos_abbr`, `pos_name`, `pos_sport_code`, `pos_eligible_positions`, `created_at`, `updated_at`) VALUES ('SG', 'SG', 'Shooting Guard', 'nba', 'SG', NOW(), NOW());
INSERT INTO `laraveldb`.`positions` (`pos_key`, `pos_abbr`, `pos_name`, `pos_sport_code`, `pos_eligible_positions`, `created_at`, `updated_at`) VALUES ('G', 'G', 'Guard', 'nba', 'SG, PG', NOW(), NOW());
INSERT INTO `laraveldb`.`positions` (`pos_key`, `pos_abbr`, `pos_name`, `pos_sport_code`, `pos_eligible_positions`, `created_at`, `updated_at`) VALUES ('SF', 'SF', 'Small Forward', 'nba', 'SF', NOW(), NOW());
INSERT INTO `laraveldb`.`positions` (`pos_key`, `pos_abbr`, `pos_name`, `pos_sport_code`, `pos_eligible_positions`, `created_at`, `updated_at`) VALUES ('PF', 'PF', 'Power Forward', 'nba', 'PF', NOW(), NOW());
INSERT INTO `laraveldb`.`positions` (`pos_key`, `pos_abbr`, `pos_name`, `pos_sport_code`, `pos_eligible_positions`, `created_at`, `updated_at`) VALUES ('F', 'F', 'Forward', 'nba', 'SF, PF', NOW(), NOW());
INSERT INTO `laraveldb`.`positions` (`pos_key`, `pos_abbr`, `pos_name`, `pos_sport_code`, `pos_eligible_positions`, `created_at`, `updated_at`) VALUES ('C', 'C', 'Center', 'nba', 'C', NOW(), NOW());
INSERT INTO `laraveldb`.`positions` (`pos_key`, `pos_abbr`, `pos_name`, `pos_sport_code`, `pos_eligible_positions`, `created_at`, `updated_at`) VALUES ('UTIL', 'UTIL', 'Util', 'nba', 'SF,C,SG,PF,PG', NOW(), NOW());


INSERT INTO `laraveldb`.`lineups`
(`lu_id`,
`lus_con_code`,
`lus_sport_code`,
`created_at`,
`updated_at`)
SELECT `nba_contest_lineup`.`id`,
    `nba_contest_entry`.`contest_id`,
    'nba',
    `nba_contest_lineup`.`created_at`,
    `nba_contest_lineup`.`updated_at`
FROM `homestead`.`nba_contest_lineup` JOIN homestead.nba_contest_entry 
ON homestead.nba_contest_entry.id = homestead.nba_contest_lineup.entry_id;

INSERT INTO `laraveldb`.`lineup_slots`
(`lus_lu_id`,
`lus_player_code`,
`lus_pos_id`,
`created_at`,
`updated_at`)
SELECT `nba_contest_lineup`.`id`,
    `nba_contest_lineup`.`pos_pg`,
    1,
    `nba_contest_lineup`.`created_at`,
    `nba_contest_lineup`.`updated_at`
FROM `homestead`.`nba_contest_lineup`;


INSERT INTO `laraveldb`.`lineup_slots`
(`lus_lu_id`,
`lus_player_code`,
`lus_pos_id`,
`created_at`,
`updated_at`)
SELECT `nba_contest_lineup`.`id`,
    `nba_contest_lineup`.`pos_sg`,
    2,
    `nba_contest_lineup`.`created_at`,
    `nba_contest_lineup`.`updated_at`
FROM `homestead`.`nba_contest_lineup`;

INSERT INTO `laraveldb`.`lineup_slots`
(`lus_lu_id`,
`lus_player_code`,
`lus_pos_id`,
`created_at`,
`updated_at`)
SELECT `nba_contest_lineup`.`id`,
    `nba_contest_lineup`.`pos_g`,
    3,
    `nba_contest_lineup`.`created_at`,
    `nba_contest_lineup`.`updated_at`
FROM `homestead`.`nba_contest_lineup`;

INSERT INTO `laraveldb`.`lineup_slots`
(`lus_lu_id`,
`lus_player_code`,
`lus_pos_id`,
`created_at`,
`updated_at`)
SELECT `nba_contest_lineup`.`id`,
    `nba_contest_lineup`.`pos_sf`,
    4,
    `nba_contest_lineup`.`created_at`,
    `nba_contest_lineup`.`updated_at`
FROM `homestead`.`nba_contest_lineup`;

INSERT INTO `laraveldb`.`lineup_slots`
(`lus_lu_id`,
`lus_player_code`,
`lus_pos_id`,
`created_at`,
`updated_at`)
SELECT `nba_contest_lineup`.`id`,
    `nba_contest_lineup`.`pos_pf`,
    5,
    `nba_contest_lineup`.`created_at`,
    `nba_contest_lineup`.`updated_at`
FROM `homestead`.`nba_contest_lineup`;

INSERT INTO `laraveldb`.`lineup_slots`
(`lus_lu_id`,
`lus_player_code`,
`lus_pos_id`,
`created_at`,
`updated_at`)
SELECT `nba_contest_lineup`.`id`,
    `nba_contest_lineup`.`pos_f`,
    6,
    `nba_contest_lineup`.`created_at`,
    `nba_contest_lineup`.`updated_at`
FROM `homestead`.`nba_contest_lineup`;

INSERT INTO `laraveldb`.`lineup_slots`
(`lus_lu_id`,
`lus_player_code`,
`lus_pos_id`,
`created_at`,
`updated_at`)
SELECT `nba_contest_lineup`.`id`,
    `nba_contest_lineup`.`pos_c`,
    7,
    `nba_contest_lineup`.`created_at`,
    `nba_contest_lineup`.`updated_at`
FROM `homestead`.`nba_contest_lineup`;

INSERT INTO `laraveldb`.`lineup_slots`
(`lus_lu_id`,
`lus_player_code`,
`lus_pos_id`,
`created_at`,
`updated_at`)
SELECT `nba_contest_lineup`.`id`,
    `nba_contest_lineup`.`pos_util`,
    8,
    `nba_contest_lineup`.`created_at`,
    `nba_contest_lineup`.`updated_at`
FROM `homestead`.`nba_contest_lineup`;