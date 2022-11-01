require('chai')
  .should();

const { compressType, sortTypes } = require('./types.js');

describe('a qualified type name', () => {
  it('should return the same name if it is in the default package', function () {
    const className = 'ClassName';
    const actual = compressType(className);
    actual.should.equal('ClassName');
  });

  it('should return the same name if it is within 32 chars', function () {
    const className = 'com.example.ClassName';
    const actual = compressType(className);
    actual.should.equal('com.example.ClassName');
  });

  it('should return a shortened package name if it is beyond 32 chars', function () {
    const className = 'com.example.viz.graphs.bundling.ClassName';
    const actual = compressType(className);
    actual.should.equal('c.e.v.g.b.ClassName');
  });

  it('should return a shortened class name for classes in default packages', function () {
    const className = 'ThisClassIsInTheDefaultPackageButIsLong';
    const actual = compressType(className);
    actual.should.equal('ThisClassIsInThe...PackageButIsLong');
  });
});

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
