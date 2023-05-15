import {TokenStat} from "./vocabulary/model/TokenStat";
import {Token} from "./vocabulary/model/Token";

export let typeTokenStats = [
  new TokenStat(new Token("void"), 25),
  new TokenStat(new Token("Attachment"), 10),
  new TokenStat(new Token("boolean"), 9),
  new TokenStat(new Token("String"), 4),
  new TokenStat(new Token("Poll"), 4),
  new TokenStat(new Token("int"), 4),
  new TokenStat(new Token("List"), 3),
  new TokenStat(new Token("View"), 3),
  new TokenStat(new Token("Status"), 2),
  new TokenStat(new Token("Bundle"), 2),
  new TokenStat(new Token("Activity"), 2),
  new TokenStat(new Token("Shuffle"), 2),
  new TokenStat(new Token("FragmentManager"), 2),
  new TokenStat(new Token("TransactionManager"), 2),
  new TokenStat(new Token("ArrayList"), 2),
  new TokenStat(new Token("Toolbar"), 1),
  new TokenStat(new Token("TextView"), 1),
  new TokenStat(new Token("ProgressBar"), 1),
  new TokenStat(new Token("SelfUpdateStateChangedEvent"), 1),
  new TokenStat(new Token("UpdateState"), 1),
  new TokenStat(new Token("Holder"), 1),
  new TokenStat(new Token("StatusCreatedEvent"), 1),
  new TokenStat(new Token("Configuration"), 1),
  new TokenStat(new Token("MenuItem"), 1),
  new TokenStat(new Token("Menu"), 1),
  new TokenStat(new Token("MenuInflater"), 1),
  new TokenStat(new Token("APIRequest"), 1),
  new TokenStat(new Token("AnimatorSet"), 1),
  new TokenStat(new Token("Button"), 1),
  new TokenStat(new Token("ImageView"), 1),
  new TokenStat(new Token("CheckBox"), 1),
  new TokenStat(new Token("ImageButton"), 1),
];

export let wordTokenStats = [
  new TokenStat(new Token("on"), 15),
  new TokenStat(new Token("posts"), 8),
  new TokenStat(new Token("new"), 7),
];

export let classJson = `
{
  "name": "Tumbleweed",
  "version": "0.5.0"
}
`;
