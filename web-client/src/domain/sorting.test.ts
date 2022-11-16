require('chai')
  .should();

const {
  sortTypes,
} = require('./sorting');

describe('a set of types', () => {
  it('should be sorted in alphabetical order', function () {
    const types = [
      'org.thoughtcrime.securesms.mediapreview.MediaPreviewFragment$Events',
      'org.thoughtcrime.securesms.conversation.mutiselect.forward.MultiselectForwardBottomSheet$Callback',
      'org.thoughtcrime.securesms.stories.StorySlateView$Callback',
      'org.thoughtcrime.securesms.stories.viewer.text.StoryTextPostPreviewFragment$Callback',
      'org.thoughtcrime.securesms.stories.viewer.reply.StoriesSharedElementCrossFaderView$Callback',
    ];
    const actual = sortTypes(types);
    actual.should.deep.equal([
      'org.thoughtcrime.securesms.conversation.mutiselect.forward.MultiselectForwardBottomSheet$Callback',
      'org.thoughtcrime.securesms.mediapreview.MediaPreviewFragment$Events',
      'org.thoughtcrime.securesms.stories.StorySlateView$Callback',
      'org.thoughtcrime.securesms.stories.viewer.reply.StoriesSharedElementCrossFaderView$Callback',
      'org.thoughtcrime.securesms.stories.viewer.text.StoryTextPostPreviewFragment$Callback',
    ]);
  });
});
